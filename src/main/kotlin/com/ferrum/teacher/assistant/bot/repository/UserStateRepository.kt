/**
 *  Author: BurningPapaya
 *  Date: 13.04.2020
 *  Time: 21:17
 */
package com.ferrum.teacher.assistant.bot.repository

import com.ferrum.teacher.assistant.bot.model.UserState
import org.springframework.data.repository.CrudRepository

interface UserStateRepository : CrudRepository<UserState, Int> {
}