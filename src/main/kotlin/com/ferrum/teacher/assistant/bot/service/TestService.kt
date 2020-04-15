/**
 *  Author: BurningPapaya
 *  Date: 12.04.2020
 *  Time: 21:48
 */
package com.ferrum.teacher.assistant.bot.service

import com.ferrum.teacher.assistant.bot.constants.State
import com.ferrum.teacher.assistant.bot.dto.CurrentTestDto
import com.ferrum.teacher.assistant.bot.dto.SessionData
import com.ferrum.teacher.assistant.bot.model.Test
import com.ferrum.teacher.assistant.bot.repository.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TestService
@Autowired constructor(
        private val testRepository: TestRepository,
        private val sessionService: UserSessionService
){

    fun newTest (update: Update) : SendMessage {
        // prepare reply
        val reply = SendMessage()
        reply.text = "Дайте название вашему тесту (не больше 255 символов)"
        reply.chatId = update.message.chatId.toString()

        // update user's state
        sessionService.saveUserState(reply.chatId, State.NEW_TEST)

        return reply
    }

    fun createTest(update: Update) : SendMessage {
        // Todo create exception class and ErrorCodes
        if (!update.hasMessage() || !update.message.hasText())
            throw RuntimeException("No Message received")

        // get userId
        val userId = update.message.chatId.toString()
        // cut if needed the testName
        val testName = if (update.message.text.length > 255) update.message.text.substring(0, 255) else update.message.text
        // initialize test entity
        val newTest = Test(
                testName,
                userId,
                update.message.chat.userName
        )
        // save new entity
        val test = testRepository.save(newTest)
        // prepare session data
        val sessionData = SessionData(CurrentTestDto(test.id))

        // update user's state
        sessionService.saveUserState(update.message.chatId.toString(), State.NEW_QUESTION, sessionData)

        // prepare reply
        val reply = SendMessage()
        reply.text = "Тест: **${newTest.name}**\nВопрос 1: (Введите текст вопроса. Не более 500 символов)"
        reply.chatId = userId

        return reply
    }

    fun getTest (testId: Int) : Test? {
        return testRepository.findByIdOrNull(testId)
    }
}