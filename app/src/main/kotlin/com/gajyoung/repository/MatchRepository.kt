package com.gajyoung.repository

import com.gajyoung.riot.dto.Match
import org.jooq.DSLContext
import org.jooq.generated.Tables.MATCHES
import org.jooq.generated.tables.records.MatchesRecord
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class MatchRepository(private val dslContext: DSLContext) {

    fun isMatchInTable(matchId: String) =
        dslContext.selectFrom(MATCHES)
            .where(MATCHES.MATCH_ID.eq(matchId))
            .fetch()
            .isNotEmpty

    fun insertMatches(matches: List<String>, puuid: String) {
        dslContext
            .batchMerge(matches.toMatchRecords(puuid))
            .execute()
    }

    fun insertMatch(matchId: String, puuid: String, match: Match) {
        dslContext.insertInto(MATCHES)
            .set(matchId.toMatchRecord(puuid))
            .execute()

        // TODO: insert more data
    }

    fun List<String>.toMatchRecords(puuid: String) =
        map { MatchesRecord(it, puuid) }

    fun String.toMatchRecord(puuid: String) =
        MatchesRecord(this, puuid)
}