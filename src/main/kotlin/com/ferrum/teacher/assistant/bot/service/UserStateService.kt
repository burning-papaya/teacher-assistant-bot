/**
 *  Author: BurningPapaya
 *  Date: 13.04.2020
 *  Time: 21:21
 */
package com.ferrum.teacher.assistant.bot.service

import com.ferrum.teacher.assistant.bot.constants.State
import com.ferrum.teacher.assistant.bot.model.UserState
import com.ferrum.teacher.assistant.bot.repository.UserStateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserStateService
@Autowired constructor(
        private val userStateRepository: UserStateRepository
) {

    fun getUserState (userId: String) : State {
        return userStateRepository.findByUserId(userId)?.state ?: State.MAIN_MENU
    }

    fun saveUserState (userId: String, state: State) : UserState{
        val userState = UserState()
        userState.userId = userId
        userState.state = state

        return userStateRepository.save(userState)
    }

}