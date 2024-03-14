package com.gajyoung.repository

import org.jooq.DSLContext
import org.jooq.generated.Tables.RIOTACCOUNT
import org.jooq.generated.tables.records.RiotaccountRecord
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class RiotAccountRepository(private val dslContext: DSLContext) {

    fun saveRiotAccount(riotAccountsRecord: RiotaccountRecord) {
        dslContext
            .insertInto(RIOTACCOUNT)
            .set(riotAccountsRecord)
            .onDuplicateKeyUpdate()
            .set(RIOTACCOUNT.GAME_NAME, riotAccountsRecord.gameName)
            .execute()
    }
}