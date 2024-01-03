package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.DeleteRegisteredEventUseCase
import org.junit.Assert.*
import org.junit.Test

class DeleteRegisteredEventUiConverterTest {
    private val converter = DeleteRegisteredEventUiConverter()

    @Test
    fun testConvertSuccess() {
        val itemMock = true
        val response = DeleteRegisteredEventUseCase.Response(itemMock)
        val expectedResult = true

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}