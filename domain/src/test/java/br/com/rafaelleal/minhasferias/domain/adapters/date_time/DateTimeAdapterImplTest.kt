package br.com.rafaelleal.minhasferias.domain.adapters.date_time

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime

class DateTimeAdapterImplTest{

    val dateTimeAdapter = DateTimeAdapterImpl()

    @Test
    fun toDateTimeStrings(){
        val input = LocalDateTime.of( 2023, 1, 12, 14, 35)
        val output = DateTimeStrings("12/01/2023", "14:35")
        assertEquals(output.date, dateTimeAdapter.toDateTimeStrings(input).date)
        assertEquals(output.time, dateTimeAdapter.toDateTimeStrings(input).time)
    }

    @Test
    fun toFormattedString(){
        val input = LocalDateTime.of( 2023, 11, 29, 8, 7)
        val output = "29/11/2023 - 08:07"
        assertEquals(output, dateTimeAdapter.toFormattedString(input))
    }

    @Test
    fun toDateTime(){
        val input = DateTimeStrings("23/08/2023", "01:59")
        val output = LocalDateTime.of(2023,8,23, 1,59)
        assertEquals(input.date, dateTimeAdapter.toDateTimeStrings(output).date)
        assertEquals(input.time, dateTimeAdapter.toDateTimeStrings(output).time)
    }


}