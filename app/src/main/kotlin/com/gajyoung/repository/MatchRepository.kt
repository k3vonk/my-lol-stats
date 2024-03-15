package com.gajyoung.repository

import com.gajyoung.riot.dto.Info
import com.gajyoung.riot.dto.Match
import com.gajyoung.riot.dto.Metadata
import com.gajyoung.riot.dto.Participant
import org.jooq.DSLContext
import org.jooq.generated.Tables.INFO
import org.jooq.generated.Tables.MATCH
import org.jooq.generated.Tables.METADATA
import org.jooq.generated.tables.records.InfoRecord
import org.jooq.generated.tables.records.MatchRecord
import org.jooq.generated.tables.records.MetadataRecord
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

    fun insertMatches(
        matches: List<String>,
        puuid: String,
    ) {
        dslContext
            .batchMerge(matches.toMatchRecords(puuid))
            .execute()
    }

    fun insertMatch(
        matchId: String,
        puuid: String,
        match: Match,
    ) {
        dslContext.insertInto(MATCH)
            .set(matchId.toMatchRecord(puuid))
            .execute()

        dslContext.insertInto(METADATA)
            .set(match.metadata.toRecord())
            .execute()

        dslContext.insertInto(INFO)
            .set(match.info.toRecord(matchId))
            .execute()

        dslContext.batchInsert(match.info.participants.map { it.toRecord(matchId) })
            .execute()
    }

    fun List<String>.toMatchRecords(puuid: String) = map { MatchRecord(it, puuid) }

    fun String.toMatchRecord(puuid: String) = MatchRecord(this, puuid)

    fun Metadata.toRecord() =
        MetadataRecord(
            matchId,
            dataVersion,
            participants.toTypedArray(),
        )

    fun Info.toRecord(matchId: String) =
        InfoRecord(
            matchId,
            gameId,
            mapId,
            queueId,
            platformId,
            gameCreation,
            gameDuration,
            gameEndTimestamp,
            gameStartTimestamp,
            gameMode,
            gameName,
            gameType,
            gameVersion,
        )

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
