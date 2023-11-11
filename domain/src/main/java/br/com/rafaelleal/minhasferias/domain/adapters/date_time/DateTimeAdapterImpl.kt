package br.com.rafaelleal.minhasferias.domain.adapters.date_time

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal const val DATE_FORMAT = "dd/MM/yyyy"
internal const val TIME_FORMAT = "HH:mm"
internal const val DATE_TIME_FORMAT = "dd/MM/yyyy - HH:mm"

class DateTimeAdapterImpl: DateTimeAdapter {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun toDateTimeStrings(localDateTime: LocalDateTime): DateTimeStrings {
        val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        val timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
        return DateTimeStrings(
            dateFormatter.format(localDateTime),
            timeFormatter.format(localDateTime)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toFormattedString(localDateTime: LocalDateTime): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        return dateTimeFormatter.format(localDateTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toDateTime(dateTimeStrings: DateTimeStrings): LocalDateTime {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        val parsedDateTime = "${dateTimeStrings.date} - ${dateTimeStrings.time}"
        val date = LocalDateTime.parse(parsedDateTime, dateTimeFormatter)
        return date
    }

}