/**
Author: BurningPapaya
Date: 08.04.2020
Time: 22:23
 */
package com.ferrum.teacher.assistant.bot.service

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update


@Component
class TeacherAssistantBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        return "Teacher Assistant Bot"
    }

    override fun getBotToken(): String {
        return "1236603026:AAFxrmfu79p1sLRLAp8PSkZeI0KMUATHyCw"
    }

    override fun onUpdateReceived(p0: Update?) {
        logger.info("Update received")
    }

    companion object {
        private val logger = LogManager.getLogger(TeacherAssistantBot::class.java)
    }
}