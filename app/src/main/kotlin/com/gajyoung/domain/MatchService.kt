package com.gajyoung.domain

import com.gajyoung.repository.MatchRepository
import com.gajyoung.riot.api.query.MatchQueryParameters
import com.gajyoung.riot.dto.Match
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class MatchService(
    val europeApiWebClient: WebClient,
    val accountService: AccountService,
    val matchRepository: MatchRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    // TODO: this is only for testing
    fun getFirstMatch(matchQueryParameters: MatchQueryParameters) =
        getMatches(matchQueryParameters)
            .flatMap { Mono.just(it.first()) }
            .flatMap { matchId ->
                getMatch(matchId)
            }

    // TODO what if getSummoner is null?
    // TODO: make it run less
    fun getMatches(matchQueryParameters: MatchQueryParameters): Mono<MutableList<String>> {
        val matches =
            europeApiWebClient.get()
                .uri { it.buildMatchUri(matchQueryParameters) }
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<List<String>>() {})
                .flatMapMany { Flux.fromIterable(it) }
                .collectList()
                .flatMap { matches ->
                    Flux.fromIterable(matches)
                        .publishOn(Schedulers.boundedElastic())
                        .filter { matchId -> !matchRepository.isMatchInTable(matchId) }
                        .publishOn(Schedulers.boundedElastic())
                        .flatMap { matchId ->
                            logger.info("Inserting match: $matchId")
                            getMatch(matchId)
                                .publishOn(Schedulers.boundedElastic())
                                .doOnNext {
                                    matchRepository.insertMatch(matchId, accountService.getAccount().puuid, it)
                                    logger.info(it.toString())
                                }
                                .thenReturn(matchId)
                        }
                        .collectList()
                }

        return matches
    }

    private fun getMatch(matchId: String) =
        europeApiWebClient.get()
            .uri("/lol/match/v5/matches/$matchId")
            .retrieve()
            .bodyToMono(Match::class.java)

    private fun UriBuilder.buildMatchUri(matchQueryParameters: MatchQueryParameters) =
        path("/lol/match/v5/matches/by-puuid/${accountService.getAccount().puuid}/ids")
            .setMatchQueryParameters(matchQueryParameters)
            .build()

    fun UriBuilder.setMatchQueryParameters(matchQueryParameters: MatchQueryParameters) =
        apply {
            matchQueryParameters.startTime?.let { queryParam("startTime", it) }
            matchQueryParameters.endTime?.let { queryParam("endTime", it) }
            matchQueryParameters.queue?.let { queryParam("queue", it) }
            matchQueryParameters.type?.let { queryParam("type", it) }
            queryParam("start", matchQueryParameters.start)
            queryParam("count", matchQueryParameters.count)
        }
}
