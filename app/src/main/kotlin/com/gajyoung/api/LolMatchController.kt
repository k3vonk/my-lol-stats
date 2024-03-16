package com.gajyoung.api

import com.gajyoung.domain.MatchService
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/lol/matches")
class LolMatchController(
    private val matchService: MatchService,
) {
    @GetMapping
    fun getMatches(
        @RequestParam queryParameters: MultiValueMap<String, String>,
    ) = matchService.getMatchesFromDB(queryParameters)

    @GetMapping("/update")
    fun getMatchesFromRiot(
        @RequestParam queryParameters: MultiValueMap<String, String>,
    ) = matchService.fetchMatchesFromRiot(queryParameters)
}
