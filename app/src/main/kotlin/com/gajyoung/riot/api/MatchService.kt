package com.gajyoung.riot.api

import com.gajyoung.domain.SummonerService
import com.gajyoung.riot.api.query.MatchQueryParameters
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

@Service
class MatchService(
    val europeApiWebClient: WebClient,
    val summonerService: SummonerService,
) {

    // TODO what if getSummoner is null?
    fun getMatches(matchQueryParameters: MatchQueryParameters) =
        europeApiWebClient.get()
            .uri {
                it.path("/lol/match/v5/matches/by-puuid/${summonerService.getSummoner()?.puuid}/ids")
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