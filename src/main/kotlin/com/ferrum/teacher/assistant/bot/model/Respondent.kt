/**
Author: BurningPapaya
Date: 11.04.2020
Time: 13:49
 */
package com.ferrum.teacher.assistant.bot.model

import javax.persistence.*

@Table(name = "respondent")
@Entity
class Respondent : BaseIntEntity() {

    @Column(name = "user_id")
    var userId: Int? = null

    @Column(name = "username")
    var username: String? = null


}