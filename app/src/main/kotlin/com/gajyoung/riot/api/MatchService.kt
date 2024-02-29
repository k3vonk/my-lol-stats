package com.gajyoung.riot.api

import com.gajyoung.domain.AccountService
import com.gajyoung.riot.api.query.MatchQueryParameters
import com.gajyoung.riot.dto.Match
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

@Service
class MatchService(
    val europeApiWebClient: WebClient,
    val summonerService: AccountService,
) {

    // TODO: this is only for testing
    fun getFirstMatch(matchQueryParameters: MatchQueryParameters) =
        getMatches(matchQueryParameters)
            .flatMap { Mono.just(it.first()) }
            .flatMap { matchId ->
                europeApiWebClient.get()
                    .uri("/lol/match/v5/matches/$matchId")
                    .retrieve()
                    .bodyToMono(Match::class.java)
            }

    // TODO what if getSummoner is null?
    fun getMatches(matchQueryParameters: MatchQueryParameters) =
        europeApiWebClient.get()
            .uri {
                it.path("/lol/match/v5/matches/by-puuid/${summonerService.getAccount()?.puuid}/ids")
                    .setMatchQueryParameters(matchQueryParameters)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<String>>() {})

    fun UriBuilder.setMatchQueryParameters(matchQueryParameters: MatchQueryParameters) = apply {
        matchQueryParameters.startTime?.let { queryParam("startTime", it) }
        matchQueryParameters.endTime?.let { queryParam("endTime", it) }
        matchQueryParameters.queue?.let { queryParam("queue", it) }
        matchQueryParameters.type?.let { queryParam("type", it) }
        queryParam("start", matchQueryParameters.start)
        queryParam("count", matchQueryParameters.count)
    }
}