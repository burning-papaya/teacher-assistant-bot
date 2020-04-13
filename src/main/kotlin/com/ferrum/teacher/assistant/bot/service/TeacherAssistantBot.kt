/**
Author: BurningPapaya
Date: 08.04.2020
Time: 22:23
 */
package com.ferrum.teacher.assistant.bot.service

import com.ferrum.teacher.assistant.bot.constants.Command
import com.ferrum.teacher.assistant.bot.constants.State
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TeacherAssistantBot
@Autowired constructor(
        private val userStateService: UserStateService,
        private val testService: TestService
) : TelegramLongPollingBot() {

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
        if (update.message?.chatId == null) {
            return
        }

        // get user's state
        val state = userStateService.getUserState(update.message?.chatId?.toString() ?: "")

        // check whether command was received
        if (update.hasMessage() && update.message.hasText() && update.message.text.startsWith("/")) {
            // get command name
            val command = update.message.text.substring(1)

            val reply = when (Command.valueOf(command.toUpperCase())) {
                Command.CREATE -> testService.newTest(update)
                else -> defaultReply (update)
            }

            execute(reply)
            return
        }

        logger.info(update.message.text)
        logger.info("UserId: " + update.message.chatId)

        val response = SendMessage()

        response.chatId = update.message.chatId.toString()
        response.text = "Received ${update.message.text}"

        execute(response)
    }

    fun defaultReply(update: Update) : SendMessage {
        userStateService.saveUserState(update.message!!.chatId!!.toString(), State.MAIN_MENU)

        val reply = SendMessage()
        reply.enableMarkdown(true)
        reply.text = "Для работы нажмите **/** и выберите команду"
        reply.chatId = update.message.chatId.toString()

        return reply
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TeacherAssistantBot::class.java)
    }
}
