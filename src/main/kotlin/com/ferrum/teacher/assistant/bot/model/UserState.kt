/**
 *  Author: BurningPapaya
 *  Date: 13.04.2020
 *  Time: 21:08
 */
package com.ferrum.teacher.assistant.bot.model

import com.ferrum.teacher.assistant.bot.constants.State
import javax.persistence.*

@Entity
@Table(name = "user_state")
class UserState() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "user_id")
    var userId: String? = null

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    var state: State? = null

    constructor (userId: String) : this() {
        this.userId = userId
    }
}