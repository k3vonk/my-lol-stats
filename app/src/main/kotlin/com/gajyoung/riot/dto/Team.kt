package com.gajyoung.riot.dto

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val teamId: Int,
    val win: Boolean,
)
