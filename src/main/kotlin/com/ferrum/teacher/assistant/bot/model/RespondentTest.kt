/**
Author: BurningPapaya
Date: 11.04.2020
Time: 16:12
 */
package com.ferrum.teacher.assistant.bot.model

import javax.persistence.*

@Entity
@Table(name = "respondent_test")
class RespondentTest : BaseIntEntity() {

    @Column(name = "is_active", columnDefinition = "BIT")
    var active: Boolean = false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    var test: Test? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id")
    var respondent: Respondent? = null
}