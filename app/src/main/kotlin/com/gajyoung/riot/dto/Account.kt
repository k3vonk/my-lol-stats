package com.gajyoung.riot.dto

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val puuid: String,
    val gameName: String,
    val tagLine: String,
)
