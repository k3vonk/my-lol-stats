package com.gajyoung.riot.api.query

/**
 * @param startTime Epoch timestamp in seconds. The matchlist started storing timestamps on June 16th, 2021. Any matches played before June 16th, 2021 won't be included in the results if the startTime filter is set.
 * @param endTime Epoch timestamp in seconds
 * @param queue Filter the list of match ids by a specific queue id. This filter is mutually inclusive of the type filter meaning any match ids returned must match both the queue and type filters.
 * @param type Filter the list of match ids by the type of match. This filter is mutually inclusive of the queue filter meaning any match ids returned must match both the queue and type filters.
 * @param start Defaults to 0. Start index.
 * @param count Defaults to 20. Valid values: 0 to 100. Number of match ids to return.
 */
data class MatchQueryParameters(
    val startTime: Long? = null,
    val endTime: Long? = null,
    val queue: Int? = null,
    val type: String? = null,
    val start: Int = 0,
    val count: Int = 20,
)
