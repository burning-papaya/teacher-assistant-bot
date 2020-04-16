/**
 *  Author: BurningPapaya
 *  Date: 15.04.2020
 *  Time: 18:50
 */
package com.ferrum.teacher.assistant.bot.service

import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ferrum.teacher.assistant.bot.constants.State
import com.ferrum.teacher.assistant.bot.dto.SessionData
import com.ferrum.teacher.assistant.bot.model.Question
import com.ferrum.teacher.assistant.bot.repository.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
class QuestionService
@Autowired constructor(
        private val sessionService: UserSessionService,
        private val questionRepository: QuestionRepository,
        private val testService: TestService
) {

    fun createQuestion (update: Update) : SendMessage {
        // get user id
        val userId = update.message.chatId.toString()
        // get user session
        val session = sessionService.getSession(userId)

        if (session?.sessionData == null ) {
            throw RuntimeException("Goto main menu") //Todo add exception and code
        }
        // get session data
        val sessionData = session.sessionData

        if ( sessionData!!.currentTest?.testId == null) {
            throw RuntimeException("Goto test creation")
        }

        // get test
        val test = testService.getTest(sessionData.currentTest!!.testId!!) ?: throw RuntimeException("Goto test creation")
        // cut the question text if needed
        val questionText = if (update.message.text.length > 500) update.message.text.substring(0, 501) else update.message.text
        // initialize question entity and save it
        var question = Question(test, questionText)
        question = questionRepository.save(question)

        // set question in session data and increment question counter
        sessionData.currentTest!!.questionId = question.id
        if (sessionData.currentTest!!.questionCount == null)
            sessionData.currentTest!!.questionCount = 1
        else
            sessionData.currentTest!!.questionCount!!.inc()

        // save session for user
        sessionService.saveUserState(userId, State.CORRECT_CHOICE, sessionData)

        // prepare reply
        val reply = SendMessage()
        reply.text = "Введите правильный вариант ответа (не более 100 символов) или выберите вариант из предоставленных по умолчанию"
        reply.chatId = userId
        reply.replyMarkup = defaultChoices()

        return reply
    }

    fun addChoiceToQuestion (update: Update) : SendMessage {
        // get user id
        val userId = update.message.chatId.toString()
        // get user session
        val session = sessionService.getSession(userId)

        if (session?.sessionData == null ) {
            throw RuntimeException("Goto main menu") //Todo add exception and code
        }
        // get session data
        val sessionData = session.sessionData

        if ( sessionData!!.currentTest?.questionId == null ||
                sessionData.currentTest?.questionCount == null || sessionData.currentTest?.questionCount == 0) {
            throw RuntimeException("Goto question creation")
        }

        // find question
        val question = questionRepository.findByIdOrNull(sessionData.currentTest?.questionId)
                ?: throw RuntimeException("Goto question creation")

        if (session.state == State.CORRECT_CHOICE) {
            question.correctAnswer = update.message.text
            question.otherAnswers = "[]"
            sessionData.currentTest!!.choiceCount = 1
        } else {
            val choices : MutableList<String> = jacksonObjectMapper()
                    .readValue(
                            question.otherAnswers,
                            TypeFactory.defaultInstance().constructCollectionLikeType(MutableList::class.java, String::class.java)
                    )
            choices.add(update.message.text)
            question.otherAnswers = jacksonObjectMapper().writeValueAsString(choices)
            sessionData.currentTest!!.choiceCount!!.inc()
        }

        questionRepository.save(question)
        sessionService.saveUserState(userId, State.NEW_CHOICE, sessionData)

        // prepare reply
        val reply = SendMessage()
        reply.text = "Введите еще вариант ответа или перейдите к созданию нового вопроса"
        reply.chatId = userId
        reply.replyMarkup = defaultChoices()

        return reply
    }



    private fun defaultChoices () : ReplyKeyboard {
        val keyboardRow = KeyboardRow()
        keyboardRow.add(KeyboardButton("Все варианты верны"))
        keyboardRow.add(KeyboardButton("Верного варианта нет"))

        val keyboard = ReplyKeyboardMarkup()
        keyboard.oneTimeKeyboard = true
        keyboard.keyboard = listOf(keyboardRow)

        return keyboard
    }

}