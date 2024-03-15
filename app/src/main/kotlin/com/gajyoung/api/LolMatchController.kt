package com.gajyoung.api

import com.gajyoung.domain.MatchService
import com.gajyoung.riot.api.LeagueMatchApi
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/lol/match")
class LolMatchController(
    private val matchService: MatchService,
    private val leagueMatchApi: LeagueMatchApi,
) {
    @GetMapping
    fun getMatches(
        @RequestParam queryParameters: MultiValueMap<String, String>,
    ) = matchService.getMatches(queryParameters)

    @GetMapping("/test")
    fun getMatchIds(
        @RequestParam queryParameters: MultiValueMap<String, String>,
    ) = leagueMatchApi.getMatchIds(
        "xFpq6Dx5JZ7gtS5g-_aw9hZRCNF6S6QnMO-PU64YpCKP93nACZPrtiG1R3aejrLLtsIVVF2201vXxw",
        queryParameters,
    )

    @GetMapping("/test/first")
    fun getMatch() = leagueMatchApi.getMatch("EUW1_6856461913")
}
