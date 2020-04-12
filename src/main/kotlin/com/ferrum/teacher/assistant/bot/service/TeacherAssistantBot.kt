/**
Author: BurningPapaya
Date: 08.04.2020
Time: 22:23
 */
package com.ferrum.teacher.assistant.bot.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class TeacherAssistantBot : TelegramLongPollingBot() {

    @Value("\${telegram.bot.name}")
    private val telegramBotName: String = ""

    @Value("\${telegram.bot.token}")
    private val telegramBotToken: String = ""

    override fun getBotUsername(): String {
        return telegramBotName
    }

    override fun getBotToken(): String {
        return telegramBotToken
    }

    override fun onUpdateReceived(update: Update) {
        // check whether command was received
        if (update.hasMessage() && update.message.hasText() && update.message.text.startsWith("/")) {
            val command = update.message.text.substring(1)

        }

        logger.info(update.message.text)
        logger.info("UserId: " + update.message.chatId)

        val response = SendMessage()

        response.chatId = update.message.chatId.toString()
        response.text = "Received ${update.message.text}"

        execute(response)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TeacherAssistantBot::class.java)
    }
}
