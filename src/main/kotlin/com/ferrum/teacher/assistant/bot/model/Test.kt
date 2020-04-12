/**
Author: BurningPapaya
Date: 11.04.2020
Time: 15:55
 */
package com.ferrum.teacher.assistant.bot.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "test")
class Test : BaseIntEntity() {

    var name: String? = null

    @Column(name = "author_id")
    var authorId: String? = null

    @Column(name = "author_name")
    var authorName: String? = null

    @Column(name = "code")
    var code: String? = null

    @Column(name = "is_deleted", columnDefinition = "BIT")
    var deleted: Boolean = false

    @Column(name = "is_completed", columnDefinition = "BIT")
    var completed: Boolean = false
}