package com.gajyoung.domain

import com.gajyoung.domain.stats.ChampionStats
import com.gajyoung.repository.MatchRepository
import com.gajyoung.riot.dto.Match
import com.gajyoung.riot.dto.Participant
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
class StatService(
    val accountService: AccountService,
    val matchRepository: MatchRepository,
) {
    fun getChampionStats(queryParameters: MultiValueMap<String, String>): List<ChampionStats> {
        val puuid = accountService.getPuiid()
        val matches = matchRepository.getMatches(puuid, queryParameters)

        return matches.flatMap { match -> match.participantsWithPuuid(puuid) }
            .groupBy { it.championName }
            .map { (championName, participants) ->
                participants.setupChampionStats(championName)
            }
            .sortedByDescending { it.wins }
    }

    private fun List<Participant>.setupChampionStats(championName: String) =
        ChampionStats(
            championName = championName,
            wins = count { it.win },
            loses = size - count { it.win },
            played = size,
        )

    private fun Match.participantsWithPuuid(puuid: String) = info.participants.filter { it.puuid == puuid }
}
