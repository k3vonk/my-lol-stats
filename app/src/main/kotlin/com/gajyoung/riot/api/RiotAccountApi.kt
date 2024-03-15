package com.gajyoung.riot.api

import com.gajyoung.riot.dto.Account
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class RiotAccountApi(private val europeApiWebClient: WebClient) {
    fun getAccountByRiotId(
        gameName: String,
        tagLine: String,
    ) = europeApiWebClient.get()
        .uri("/riot/account/v1/accounts/by-riot-id/$gameName/$tagLine")
        .retrieve()
        .bodyToMono(Account::class.java)
}
