package br.com.rafaelleal.minhasferias.domain.adapters.date_time

import java.time.LocalDateTime

interface DateTimeAdapter {
    fun toDateTimeStrings(localDateTime: LocalDateTime): DateTimeStrings
    fun toFormattedString(localDateTime: LocalDateTime): String
    fun toDateTime(dateTimeStrings: DateTimeStrings): LocalDateTime
}

