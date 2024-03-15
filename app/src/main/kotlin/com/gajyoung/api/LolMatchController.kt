package com.gajyoung.api

import com.gajyoung.domain.MatchService
import com.gajyoung.riot.api.LeagueMatchApi
import com.gajyoung.riot.api.query.MatchQueryParameters
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lol/match")
class LolMatchController(
    private val matchService: MatchService,
    private val leagueMatchApi: LeagueMatchApi,
) {
    @GetMapping
    fun getMatches(
        @ModelAttribute matchQueryParameters: MatchQueryParameters,
    ) = matchService.getMatches(matchQueryParameters)

    @GetMapping("/first")
    fun getFirstMatch(
        @ModelAttribute matchQueryParameters: MatchQueryParameters,
    ) = matchService.getFirstMatch(matchQueryParameters)

    @GetMapping("/test")
    fun getMatchIds(
        @RequestParam queryParameters: MultiValueMap<String, String>,
    ) = leagueMatchApi.getMatchIds(
        "xFpq6Dx5JZ7gtS5g-_aw9hZRCNF6S6QnMO-PU64YpCKP93nACZPrtiG1R3aejrLLtsIVVF2201vXxw",
        queryParameters,
    )
}
