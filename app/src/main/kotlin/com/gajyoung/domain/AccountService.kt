package com.gajyoung.domain

import com.gajyoung.repository.RiotAccountRepository
import com.gajyoung.riot.api.RiotAccountApi
import com.gajyoung.riot.dto.Account
import org.jooq.generated.tables.records.RiotaccountRecord
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service

/**
 * Cache the current summoner such that it can be used for other API requests
 */
@Service
class AccountService(
    private val riotAccountApi: RiotAccountApi,
    private val riotAccountRepository: RiotAccountRepository,
) : ApplicationListener<ApplicationReadyEvent>{
    companion object {
        private const val DEFAULT_GAME_NAME = "YakumoUchiha"
        private const val DEFAULT_TAG_LINE = "EUW"
    }

    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var currentAccount: Account

    private fun setAccount(account: Account) {
        currentAccount = account
    }

    fun getAccount() = currentAccount

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        riotAccountApi.getAccount(DEFAULT_GAME_NAME, DEFAULT_TAG_LINE)
            .doOnNext {
                setAccount(it)
                logger.info("Initializing default user: $DEFAULT_GAME_NAME#$DEFAULT_TAG_LINE")
            }.block()
    }

    // TODO: Throw exception when fetch returns null & do I need to block?
    fun fetchAccount(gameName: String, tagLine: String): Account? {
        currentAccount
            .takeIf { it.gameName == gameName && it.tagLine == tagLine }
            ?.let { return it }

        return riotAccountApi.getAccount(gameName, tagLine)
                .block()
                ?.also {
                    riotAccountRepository.saveRiotAccount(it.toRiotAccountRecord())
                    setAccount(it)
                }
    }

    private fun Account.toRiotAccountRecord() = RiotaccountRecord()
        .also {
            it.puuid = puuid
            it.gameName = gameName
            it.tagLine = tagLine
        }
}