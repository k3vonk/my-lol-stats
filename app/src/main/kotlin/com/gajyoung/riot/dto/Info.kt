package com.gajyoung.riot.dto

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val gameCreation: Long,
    val gameDuration: Long,
    val gameStartTimestamp: Long,
    val gameEndTimestamp: Long,
    val gameId: Long,
    val gameMode: String,
    val gameName: String,
    val gameType: String,
    val gameVersion: String,
    val mapId: Int,
    val participants: List<Participant>,
    val platformId: String,
    val queueId: Int,
    val teams: List<Team>,
)
