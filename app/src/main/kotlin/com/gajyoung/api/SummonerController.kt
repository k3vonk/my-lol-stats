package com.gajyoung.api

import com.gajyoung.domain.SummonerService
import com.gajyoung.repository.RiotAccountRepository
import com.gajyoung.riot.api.RiotAccountApi
import com.gajyoung.riot.dto.Account
import org.jooq.generated.tables.records.RiotAccountsRecord
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summoner")
class SummonerController(
    private val riotAccountApi: RiotAccountApi,
    private val riotAccountRepository: RiotAccountRepository,
    private val summonerService: SummonerService
) {

    @GetMapping("/{gameName}/{tagLine}")
    fun getSummoner(@PathVariable gameName: String, @PathVariable tagLine: String): Account? {
        return summonerService.getSummoner(gameName, tagLine) ?: fetchAndSetSummoner(gameName, tagLine)
    }

    // TODO: Throw exception when fetch returns null
    private fun fetchAndSetSummoner(gameName: String, tagLine: String) =
        riotAccountApi.getAccount(gameName, tagLine)
            .block()
            ?.also {
                riotAccountRepository.saveRiotAccount(it.toRiotAccountRecord())
                summonerService.setSummoner(it)
            }

    private fun Account.toRiotAccountRecord() = RiotAccountsRecord()
        .also {
            it.puuid = puuid
            it.gameName = gameName
            it.tagLine = tagLine
        }
}