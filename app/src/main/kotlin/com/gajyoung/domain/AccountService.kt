package com.gajyoung.domain

import com.gajyoung.repository.RiotAccountRepository
import com.gajyoung.riot.api.RiotAccountApi
import com.gajyoung.riot.dto.Account
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Cache the current summoner such that it can be used for other API requests
 */
@Service
class AccountService(
    private val riotAccountApi: RiotAccountApi,
    private val riotAccountRepository: RiotAccountRepository,
) : ApplicationListener<ApplicationReadyEvent> {
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var currentAccount: Account

    private fun setAccount(account: Account) {
        riotAccountRepository.saveRiotAccount(account)
        currentAccount = account
    }

    fun getPuiid() = currentAccount.puuid

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        fetchAndSetAccount(DEFAULT_GAME_NAME, DEFAULT_TAG_LINE)
            .subscribe()
    }

    fun fetchAccount(
        gameName: String,
        tagLine: String,
    ): Mono<Account>? {
        currentAccount
            .takeIf { it.gameName == gameName && it.tagLine == tagLine }
            ?.let { return Mono.just(it) }

        return fetchAndSetAccount(gameName, tagLine)
    }

    private fun fetchAndSetAccount(
        gameName: String,
        tagLine: String,
    ) = riotAccountApi.getAccountByRiotId(gameName, tagLine)
        .doOnError { logger.info("Error fetching user: $gameName#$tagLine, $it") }
        .doOnSuccess {
            setAccount(it)
            logger.info("Setting default user: $gameName#$tagLine")
        }

    companion object {
        private const val DEFAULT_GAME_NAME = "YakumoUchiha"
        private const val DEFAULT_TAG_LINE = "EUW"
    }
}
