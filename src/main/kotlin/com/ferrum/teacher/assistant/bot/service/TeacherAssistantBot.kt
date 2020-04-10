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
import org.telegram.telegrambots.meta.api.objects.Update

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

    override fun onUpdateReceived(p0: Update?) {
        logger.info("Update received")
        logger.error("Oops")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TeacherAssistantBot::class.java)
    }
}
