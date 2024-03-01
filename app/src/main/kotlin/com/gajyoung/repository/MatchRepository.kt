package com.gajyoung.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class MatchRepository(private val dslContext: DSLContext) {


}