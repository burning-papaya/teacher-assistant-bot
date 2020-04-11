/**
Author: BurningPapaya
Date: 11.04.2020
Time: 16:23
 */
package com.ferrum.teacher.assistant.bot.model

import javax.persistence.*

@Entity
@Table(name = "respondent_question")
class RespondentQuestion : BaseIntEntity() {

    @Column(name = "response")
    var response: String? = null

    @Column(name = "is_correct", columnDefinition = "BIT")
    var correct: Boolean? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    var question: Question? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id")
    var respondent: Respondent? = null
}