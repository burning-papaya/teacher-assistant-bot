/**
 *  Author: BurningPapaya
 *  Date: 11.04.2020
 *  Time: 16:39
 */
package com.ferrum.teacher.assistant.bot.repository

import com.ferrum.teacher.assistant.bot.model.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository : JpaRepository<Test, Int> {
}