package com.gajyoung.api

import com.gajyoung.domain.StatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/lol/stats")
class StatController(
    private val statService: StatService,
) {
    @GetMapping("/champions")
    fun getChampionStats() = statService.getChampionStats()
}
