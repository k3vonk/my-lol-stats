package com.gajyoung.riot

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

/**
 * Generates Riot [WebClient] beans to interact with Riot API
 */
@Configuration
class RiotWebClientConfiguration(val riotProperties: RiotProperties) {

    @Bean
    fun europeApiWebClient(): WebClient =
        generateRiotWebClient(EUROPE_URL)

    @Bean
    fun euw1ApiWebClient(): WebClient =
        generateRiotWebClient(EUW1_URL)

    private fun generateRiotWebClient(baseUrl: String) = WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(X_RIOT_TOKEN, riotProperties.apiKey)
        .build()

    companion object {
        private const val EUROPE_URL = "https://europe.api.riotgames.com"
        private const val EUW1_URL = "https://euw1.api.riotgames.com"
        private const val X_RIOT_TOKEN = "X-Riot-Token"
    }
}