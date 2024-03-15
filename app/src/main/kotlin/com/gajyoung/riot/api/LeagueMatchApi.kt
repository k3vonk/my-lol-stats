package com.gajyoung.riot.api

import com.gajyoung.riot.api.query.MatchQueryParameters
import com.gajyoung.riot.dto.Match
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Service
class LeagueMatchApi(
    private val europeApiWebClient: WebClient,
) {
    fun getMatch(matchId: String): Mono<Match> {
        return europeApiWebClient.get()
            .uri("$LOL_MATCH_URL/$matchId")
            .retrieve()
            .bodyToMono(Match::class.java)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
    }

    fun getMatchIds(
        puuid: String,
        queryParameters: MultiValueMap<String, String>,
    ): Mono<List<String>> {
        return europeApiWebClient.get()
            .uri { it.setMatchQueryParameters(puuid, queryParameters) }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<String>>() {})
    }

    private fun UriBuilder.setMatchQueryParameters(
        puuid: String,
        queryParameters: MultiValueMap<String, String>,
    ) = path("$LOL_MATCH_URL/by-puuid/$puuid/ids")
        .queryParams(queryParameters.addMatchQueryParameters())
        .build()

    private fun MultiValueMap<String, String>.addMatchQueryParameters(): MultiValueMap<String, String> {
        MatchQueryParameters().apply {
            if (startTime != null) addIfAbsent("startTime", startTime.toString())
            if (endTime != null) addIfAbsent("endTime", endTime.toString())
            if (queue != null) addIfAbsent("queue", queue.toString())
            if (type != null) addIfAbsent("type", type)
            addIfAbsent("start", start.toString())
            addIfAbsent("count", count.toString())
        }
        return this
    }

    companion object {
        private const val LOL_MATCH_URL = "lol/match/v5/matches"
    }
}
