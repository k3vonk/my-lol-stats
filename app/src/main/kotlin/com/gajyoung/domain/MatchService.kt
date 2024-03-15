package com.gajyoung.domain

import com.gajyoung.repository.MatchRepository
import com.gajyoung.riot.api.LeagueMatchApi
import com.gajyoung.riot.dto.Match
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class MatchService(
    val leagueMatchApi: LeagueMatchApi,
    val accountService: AccountService,
    val matchRepository: MatchRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getMatches(queryParameters: MultiValueMap<String, String>): Flux<Match> {
        return leagueMatchApi.getMatchIds(accountService.getPuiid(), queryParameters)
            .flatMapMany { Flux.fromIterable(it) }
            .publishOn(Schedulers.boundedElastic()) // Reactive Jooq?
            .filter { matchId -> !matchRepository.isMatchInTable(matchId) }
            .flatMap { matchId ->
                getMatch(matchId)
            }
    }

    private fun getMatch(matchId: String): Mono<Match> {
        logger.info("Inserting match: $matchId")
        return leagueMatchApi.getMatch(matchId)
            .publishOn(Schedulers.boundedElastic())
            .doOnNext {
                matchRepository.insertMatch(matchId, it)
            }
    }
}
