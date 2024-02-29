package com.gajyoung.repository

import org.jooq.DSLContext
import org.jooq.generated.Tables.RIOT_ACCOUNTS
import org.jooq.generated.tables.records.RiotAccountsRecord
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class RiotAccountRepository(private val dslContext: DSLContext) {

    fun saveRiotAccount(riotAccountsRecord: RiotAccountsRecord) {
        dslContext
            .insertInto(RIOT_ACCOUNTS)
            .set(riotAccountsRecord)
            .onDuplicateKeyUpdate()
            .set(RIOT_ACCOUNTS.GAME_NAME, riotAccountsRecord.gameName)
            .execute()
    }
}