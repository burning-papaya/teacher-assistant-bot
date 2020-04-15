/**
 *  Author: BurningPapaya
 *  Date: 13.04.2020
 *  Time: 21:21
 */
package com.ferrum.teacher.assistant.bot.service

import com.ferrum.teacher.assistant.bot.constants.State
import com.ferrum.teacher.assistant.bot.model.UserSession
import com.ferrum.teacher.assistant.bot.repository.UserSessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserSessionService
@Autowired constructor(
        private val userSessionRepository: UserSessionRepository
) {

    fun getUserState (userId: String) : State {
        return userSessionRepository.findByUserId(userId)?.state ?: State.MAIN_MENU
    }

    fun saveUserState (userId: String, state: State) : UserSession {
        val userState = userSessionRepository.findByUserId(userId) ?: UserSession(userId)

        userState.state = state

        return userSessionRepository.save(userState)
    }

}