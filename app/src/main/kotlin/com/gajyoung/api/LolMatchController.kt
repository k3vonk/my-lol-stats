package com.gajyoung.api

import com.gajyoung.riot.api.MatchService
import com.gajyoung.riot.api.query.MatchQueryParameters
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lol/match")
class LolMatchController(private val matchService: MatchService) {

    @GetMapping
    fun getMatches(@ModelAttribute matchQueryParameters: MatchQueryParameters) =
        matchService.getMatches(matchQueryParameters)

    @GetMapping("/first")
    fun getFirstMatch(@ModelAttribute matchQueryParameters: MatchQueryParameters) =
        matchService.getFirstMatch(matchQueryParameters)
}