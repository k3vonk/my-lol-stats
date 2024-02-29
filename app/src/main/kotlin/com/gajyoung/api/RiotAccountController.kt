package com.gajyoung.api

import com.gajyoung.domain.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class RiotAccountController(
    private val accountService: AccountService
) {

    @GetMapping("/{gameName}/{tagLine}")
    fun getSummoner(
        @PathVariable gameName: String,
        @PathVariable tagLine: String
    ) = accountService.getAccount(gameName, tagLine) ?: accountService.fetchAndSetAccount(gameName, tagLine)
}