package com.gajyoung.repository

import com.gajyoung.riot.dto.Info
import com.gajyoung.riot.dto.Match
import com.gajyoung.riot.dto.Metadata
import com.gajyoung.riot.dto.Participant
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SelectConditionStep
import org.jooq.generated.Tables.INFO
import org.jooq.generated.Tables.MATCH
import org.jooq.generated.Tables.METADATA
import org.jooq.generated.Tables.PARTICIPANT
import org.jooq.generated.tables.records.InfoRecord
import org.jooq.generated.tables.records.ParticipantRecord
import org.jooq.impl.DSL.asterisk
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.MultiValueMap

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
        insertMatchId(matchId)
        insertMetadata(match.metadata)
        insertInfo(match.info, matchId)
        insertParticipants(match.info.participants, matchId)
    }

    fun getMatches(puuid: String): MutableList<Match> = getMatches(puuid, null)

    fun getMatches(
        puuid: String,
        queryParameters: MultiValueMap<String, String>?,
    ): MutableList<Match> {
        return dslContext.select(asterisk())
            .from(METADATA)
            .join(INFO).on(INFO.MATCH_ID.eq(METADATA.MATCH_ID))
            .join(PARTICIPANT).on(PARTICIPANT.MATCH_ID.eq(METADATA.MATCH_ID))
            .where(PARTICIPANT.PUUID.eq(puuid)).apply {
                setSearchQueries(queryParameters)
            }
            .fetch {
                val metadata = it.into(METADATA).into(Metadata::class.java)
                val participants = getParticipants(metadata.matchId)
                val info = it.into(INFO).toDataClass(participants)
                return@fetch Match(metadata, info)
            }
    }

    private fun insertMatchId(matchId: String) {
        dslContext.insertInto(MATCH)
            .set(MATCH.MATCH_ID, matchId)
            .execute()
    }

    private fun insertParticipants(
        participants: List<Participant>,
        matchId: String,
    ) {
        dslContext.batchInsert(participants.map { it.toRecord(matchId) })
            .execute()
    }

    private fun insertInfo(
        info: Info,
        matchId: String,
    ) {
        val infoRecord = dslContext.newRecord(INFO, info)
        dslContext.insertInto(INFO)
            .set(INFO.MATCH_ID, matchId)
            .set(infoRecord)
            .execute()
    }

    private fun insertMetadata(metadata: Metadata) {
        val metadataRecord = dslContext.newRecord(METADATA, metadata)
        dslContext.insertInto(METADATA)
            .set(metadataRecord)
            .execute()
    }

    private fun getParticipants(matchId: String): MutableList<Participant> =
        dslContext.selectFrom(PARTICIPANT)
            .where(PARTICIPANT.MATCH_ID.eq(matchId))
            .fetchInto(Participant::class.java)

    private fun SelectConditionStep<Record>.setSearchQueries(queryParameters: MultiValueMap<String, String>?) {
        queryParameters?.let { m ->
            m["count"]?.firstOrNull()?.toInt().let { limit(it) }
            m["start"]?.firstOrNull()?.toInt().let { offset(it) }
        }
    }

    private fun Participant.toRecord(matchId: String) =
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

    private fun InfoRecord.toDataClass(participants: List<Participant>) =
        Info(
            gameCreation = this[INFO.GAME_CREATION],
            gameDuration = this[INFO.GAME_DURATION],
            gameStartTimestamp = this[INFO.GAME_START_TIMESTAMP],
            gameEndTimestamp = this[INFO.GAME_END_TIMESTAMP],
            gameId = this[INFO.GAME_ID],
            gameMode = this[INFO.GAME_MODE],
            gameName = this[INFO.GAME_NAME],
            gameType = this[INFO.GAME_TYPE],
            gameVersion = this[INFO.GAME_VERSION],
            mapId = this[INFO.MAP_ID],
            participants = participants,
            platformId = this[INFO.PLATFORM_ID],
            queueId = this[INFO.QUEUE_ID],
        )
}
