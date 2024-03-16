package com.gajyoung.api

import com.gajyoung.domain.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class RiotAccountController(
    private val accountService: AccountService,
) {
    @GetMapping("/{gameName}/{tagLine}")
    fun getAccount(
        @PathVariable gameName: String,
        @PathVariable tagLine: String,
    ) = accountService.fetchAccount(gameName, tagLine)
}
