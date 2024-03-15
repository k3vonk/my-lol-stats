package com.gajyoung.repository

import com.gajyoung.riot.dto.Match
import com.gajyoung.riot.dto.Participant
import org.jooq.DSLContext
import org.jooq.generated.Tables.INFO
import org.jooq.generated.Tables.MATCH
import org.jooq.generated.Tables.METADATA
import org.jooq.generated.tables.records.ParticipantRecord
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class MatchRepository(private val dslContext: DSLContext) {
    fun isMatchInTable(matchId: String) =
        dslContext.selectFrom(MATCH)
            .where(MATCH.MATCH_ID.eq(matchId))
            .fetch()
            .isNotEmpty

    fun insertMatch(
        matchId: String,
        match: Match,
    ) {
        dslContext.insertInto(MATCH)
            .set(MATCH.MATCH_ID, matchId)
            .execute()

        val metadata = dslContext.newRecord(METADATA, match.metadata)
        dslContext.insertInto(METADATA)
            .set(metadata)
            .execute()

        val info = dslContext.newRecord(INFO, match.info)
        dslContext.insertInto(INFO)
            .set(INFO.MATCH_ID, matchId)
            .set(info)
            .execute()

        dslContext.batchInsert(match.info.participants.map { it.toRecord(matchId) })
            .execute()
    }

    fun Participant.toRecord(matchId: String) =
        ParticipantRecord(
            matchId,
            puuid,
            participantId,
            championId,
            championName,
            individualPosition,
            role,
            summonerId,
            summonerName,
            teamId,
            teamPosition,
            timePlayed,
        )
}
