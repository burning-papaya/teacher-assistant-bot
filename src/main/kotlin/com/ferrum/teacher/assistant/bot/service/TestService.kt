/**
 *  Author: BurningPapaya
 *  Date: 12.04.2020
 *  Time: 21:48
 */
package com.ferrum.teacher.assistant.bot.service

import com.ferrum.teacher.assistant.bot.constants.State
import com.ferrum.teacher.assistant.bot.model.Test
import com.ferrum.teacher.assistant.bot.repository.TestRepository
import org.springframework.beans.factory.annotation.Autowired
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

        // cut if needed the testName
        val testName = if (update.message.text.length > 255) update.message.text.substring(0, 255) else update.message.text
        // initialize test entity
        val newTest = Test(
                testName,
                update.message.chatId.toString(),
                update.message.chat.userName
        )
        // save new entity
        testRepository.save(newTest)

        // prepare reply
        val reply = SendMessage()
        reply.text = "Тест: **${newTest.name}**\nВопрос 1: (Введите вопрос)"
        reply.chatId = update.message.chatId.toString()

        // update user's state
        sessionService.saveUserState(reply.chatId, State.NEW_QUESTION)

        return reply
    }
}