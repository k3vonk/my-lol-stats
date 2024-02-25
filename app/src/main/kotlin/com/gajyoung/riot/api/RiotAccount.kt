package com.gajyoung.riot.api

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class RiotAccount(private val europeApiWebClient: WebClient) {

    fun getAccount(gameName: String, tagLine: String) =
        europeApiWebClient.get()
            .uri("/riot/account/v1/accounts/by-riot-id/${gameName}/${tagLine}")
            .retrieve()
            .bodyToMono(String::class.java)
}