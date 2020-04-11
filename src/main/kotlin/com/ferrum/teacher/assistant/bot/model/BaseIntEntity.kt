/**
Author: BurningPapaya
Date: 11.04.2020
Time: 14:09
 */
package com.ferrum.teacher.assistant.bot.model

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseIntEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "date_created", columnDefinition = "TIMESTAMP")
    var dateCreated: LocalDateTime? = null

    @Column(name = "date_updated", columnDefinition = "TIMESTAMP")
    var dateUpdated: LocalDateTime? = null
}