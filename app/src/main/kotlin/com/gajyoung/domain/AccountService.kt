package com.gajyoung.domain

import com.gajyoung.repository.RiotAccountRepository
import com.gajyoung.riot.api.RiotAccountApi
import com.gajyoung.riot.dto.Account
import org.jooq.generated.tables.records.RiotAccountsRecord
import org.springframework.stereotype.Service

/**
 * Cache the current summoner such that it can be used for other API requests
 */
@Service
class AccountService(
    private val riotAccountApi: RiotAccountApi,
    private val riotAccountRepository: RiotAccountRepository,
) {

    private var currentAccount: Account? = null

    private fun setAccount(account: Account) {
        currentAccount = account
    }

    fun getAccount() = currentAccount

    fun getAccount(gameName: String, tagLine: String) =
        currentAccount?.takeIf { it.gameName == gameName && it.tagLine == tagLine }

    // TODO: Throw exception when fetch returns null & do I need to block?
    fun fetchAndSetAccount(gameName: String, tagLine: String) =
        riotAccountApi.getAccount(gameName, tagLine)
            .block()
            ?.also {
                riotAccountRepository.saveRiotAccount(it.toRiotAccountRecord())
                setAccount(it)
            }

    private fun Account.toRiotAccountRecord() = RiotAccountsRecord()
        .also {
            it.puuid = puuid
            it.gameName = gameName
            it.tagLine = tagLine
        }
}