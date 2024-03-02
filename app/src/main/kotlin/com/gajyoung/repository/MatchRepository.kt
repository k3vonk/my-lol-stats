package com.gajyoung.repository

import org.jooq.DSLContext
import org.jooq.generated.tables.records.MatchesRecord
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class MatchRepository(private val dslContext: DSLContext) {

    fun insertMatches(matches: List<String>, puuid: String) {
        dslContext
            .batchMerge(matches.toMatchRecords(puuid))
            .execute()
    }

    fun List<String>.toMatchRecords(puuid: String) =
        map { MatchesRecord(it, puuid) }
}