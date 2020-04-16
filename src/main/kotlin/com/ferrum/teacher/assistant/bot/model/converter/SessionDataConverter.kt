/**
 *  Author: BurningPapaya
 *  Date: 16.04.2020
 *  Time: 15:52
 */
package com.ferrum.teacher.assistant.bot.model.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ferrum.teacher.assistant.bot.dto.SessionData
import javax.persistence.AttributeConverter


class SessionDataConverter : AttributeConverter<SessionData, String> {

    override fun convertToDatabaseColumn(sessionData: SessionData?): String? {
        return if (sessionData != null ) jacksonObjectMapper().writeValueAsString(sessionData)
        else null
    }

    override fun convertToEntityAttribute(dbColumn: String?): SessionData? {
        return if (dbColumn != null) jacksonObjectMapper().readValue(dbColumn, SessionData::class.java)
        else null
    }
}