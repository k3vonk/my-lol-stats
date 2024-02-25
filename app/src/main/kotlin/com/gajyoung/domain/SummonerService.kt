package com.gajyoung.domain

import com.gajyoung.riot.dto.Account
import org.springframework.stereotype.Service

/**
 * Cache the current summoner such that it can be used for other API requests
 */
@Service
class SummonerService {

    private var currentSummoner: Account? = null

    fun setSummoner(summoner: Account) {
        currentSummoner = summoner
    }

    fun getSummoner() = currentSummoner

    fun getSummoner(gameName: String, tagLine: String) =
        currentSummoner?.takeIf { it.gameName == gameName && it.tagLine == tagLine }
}