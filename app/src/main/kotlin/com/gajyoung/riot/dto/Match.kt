package com.gajyoung.riot.dto

import kotlinx.serialization.Serializable

@Serializable
data class Match(
    val metadata: Metadata,
    val info: Info,
)
