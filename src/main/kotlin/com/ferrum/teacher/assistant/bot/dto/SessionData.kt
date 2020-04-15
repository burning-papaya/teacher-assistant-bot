/**
 *  Author: BurningPapaya
 *  Date: 15.04.2020
 *  Time: 19:38
 */
package com.ferrum.teacher.assistant.bot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionData (
        @JsonProperty("test_creation") var currentTest: CurrentTestDto? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrentTestDto (
        @JsonProperty("test_id") var testId: Int? = null,
        @JsonProperty("question_id") var questionId: Int? = null,
        @JsonProperty("question_count") var questionCount: Int? = null,
        @JsonProperty("choice_count") var choiceCount: Int? = null
)
