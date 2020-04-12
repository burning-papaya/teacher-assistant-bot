/**
 *  Author: BurningPapaya
 *  Date: 12.04.2020
 *  Time: 21:48
 */
package com.ferrum.teacher.assistant.bot.service

import com.ferrum.teacher.assistant.bot.repository.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TestService
@Autowired constructor(
        private val testRepository: TestRepository
){

    fun newTest (update: Update) : SendMessage {
        val reply = SendMessage()

        reply.text = "Дайте название вашему тесту"
        reply.chatId = update.message.chatId.toString()

        return reply
    }

    fun createTest(update: Update) : SendMessage {
        val reply = SendMessage()

        reply.text = "Дайте название вашему тесту"
        reply.chatId = update.message.chatId.toString()

        return reply
    }
}