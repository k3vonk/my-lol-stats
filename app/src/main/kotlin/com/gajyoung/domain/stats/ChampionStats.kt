package com.gajyoung.domain.stats

data class ChampionStats(
    val championName: String,
    val wins: Int,
    val loses: Int,
    val played: Int,
)