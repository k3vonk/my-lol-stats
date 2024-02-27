package com.gajyoung.riot.dto

import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    val dataVersion: String,
    val matchId: String,
    val participants: List<String>
)