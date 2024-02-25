package com.gajyoung.api

import com.gajyoung.domain.SummonerService
import com.gajyoung.riot.api.RiotAccountService
import com.gajyoung.riot.dto.Account
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summoner")
class SummonerController(
    private val riotAccount: RiotAccountService,
    private val summonerService: SummonerService
) {

    @GetMapping("/{gameName}/{tagLine}")
    fun getSummoner(@PathVariable gameName: String, @PathVariable tagLine: String): Account? {
        return summonerService.getSummoner(gameName, tagLine) ?: fetchAndSetSummoner(gameName, tagLine)
    }

    // TODO: Throw exception when fetch returns null
    private fun fetchAndSetSummoner(gameName: String, tagLine: String) =
        riotAccount.getAccount(gameName, tagLine)
            .block()
            ?.also { summonerService.setSummoner(it) }
}