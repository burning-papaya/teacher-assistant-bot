/**
 *  Author: BurningPapaya
 *  Date: 15.04.2020
 *  Time: 18:50
 */
package com.ferrum.teacher.assistant.bot.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ferrum.teacher.assistant.bot.constants.State
import com.ferrum.teacher.assistant.bot.dto.SessionData
import com.ferrum.teacher.assistant.bot.model.Question
import com.ferrum.teacher.assistant.bot.repository.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

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
        val sessionData = jacksonObjectMapper().readValue(session.sessionData, SessionData::class.java)

        if ( sessionData.currentTest?.testId == null) {
            throw RuntimeException("Goto test creation")
        }

        // get test
        val test = testService.getTest(sessionData.currentTest!!.testId!!) ?: throw RuntimeException("Goto test creation")
        // cut the question text if needed
        val text = if (update.message.text.length > 500) update.message.text.substring(0, 501) else update.message.text
        // initialize question entity and save it
        var question = Question(test, text)
        question = questionRepository.save(question)
        // set question in session data and increment question counter
        sessionData.currentTest!!.questionId = question.id
        if (sessionData.currentTest!!.questionCount == null)
            sessionData.currentTest!!.questionCount = 1
        else
            sessionData.currentTest!!.questionCount!!.inc()

        // save session for user
        sessionService.saveUserState(userId, State.NEW_QUESTION, sessionData)

        // prepare reply
        val reply = SendMessage()
        reply.text = "Введите правильный вариант ответа (не более 100 символов)"
        reply.chatId = userId

        return reply
    }


}