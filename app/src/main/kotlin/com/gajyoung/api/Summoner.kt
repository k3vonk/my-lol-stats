package com.gajyoung.api

import com.gajyoung.riot.api.RiotAccount
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summoner")
class Summoner(private val riotAccount: RiotAccount) {

    @GetMapping("/summoner/{gameName}/{tagLine}")
    fun getSummoner(@PathVariable gameName: String, @PathVariable tagLine: String) =
        riotAccount.getAccount(gameName, tagLine)
}