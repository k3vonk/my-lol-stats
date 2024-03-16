package com.gajyoung.domain

import com.gajyoung.repository.MatchRepository
import org.springframework.stereotype.Service

@Service
class StatService(
    val accountService: AccountService,
    val matchRepository: MatchRepository,
) {
    fun getChampionStats(): Map<String, Int> {
        val matches = matchRepository.getMatches(accountService.getPuiid())

        return matches.flatMap { match ->
                match.info.participants.filter { it.puuid == accountService.getPuiid() }
            }.groupingBy { it.championName }
                .eachCount()
    }
}
