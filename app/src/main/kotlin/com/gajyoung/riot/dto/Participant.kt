package com.gajyoung.riot.dto

import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val championId: Int,
    val championName: String,
    val individualPosition: String,
    val participantId: Int,
    val puuid: String,
    val role: String,
    val summonerId: String,
    val summonerName: String,
    val teamId: Int,
    val teamPosition: String,
    val timePlayed: Int,
)