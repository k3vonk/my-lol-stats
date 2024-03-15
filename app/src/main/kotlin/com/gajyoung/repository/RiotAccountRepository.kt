package com.gajyoung.repository

import com.gajyoung.riot.dto.Account
import org.jooq.DSLContext
import org.jooq.generated.Tables.RIOTACCOUNT
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class RiotAccountRepository(private val dslContext: DSLContext) {
    fun saveRiotAccount(account: Account) {
        val riotAccount = dslContext.newRecord(RIOTACCOUNT, account)
        dslContext
            .insertInto(RIOTACCOUNT)
            .set(riotAccount)
            .onDuplicateKeyUpdate()
            .set(RIOTACCOUNT.GAME_NAME, riotAccount.gameName)
            .execute()
    }
}
