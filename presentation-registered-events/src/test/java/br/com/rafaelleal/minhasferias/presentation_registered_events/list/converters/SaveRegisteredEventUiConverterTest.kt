package br.com.rafaelleal.minhasferias.presentation_registered_events.list.converters

import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.SaveRegisteredEventUiConverter
import br.com.rafaelleal.minhasferias.usecase.registeredEvents.SaveRegisteredEventUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class SaveRegisteredEventUiConverterTest {
    private val converter = SaveRegisteredEventUiConverter()

    @Test
    fun testConvertSuccess() {
        val response = SaveRegisteredEventUseCase.Response
        val expectedResult = Unit

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}