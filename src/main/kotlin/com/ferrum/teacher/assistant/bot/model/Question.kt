/**
Author: BurningPapaya
Date: 11.04.2020
Time: 16:03
 */
package com.ferrum.teacher.assistant.bot.model

import javax.persistence.*

@Entity
@Table(name = "question")
class Question : BaseIntEntity() {

    @Column(name = "correct_answer")
    var correctAnswer: String? = null

    @Column(name = "other_answers")
    var otherAnswers: String? = null

    @JoinColumn(name = "test_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var test: Test? = null
}