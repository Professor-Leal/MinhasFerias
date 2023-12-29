package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.UpdateRegisteredEventUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class UpdateUpdateRegisteredEventUiConverterTest {
    private val converter = UpdateRegisteredEventUiConverter()

    @Test
    fun testConvertSuccess() {
        val itemMock = true
        val response = UpdateRegisteredEventUseCase.Response(itemMock)
        val expectedResult = true

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}