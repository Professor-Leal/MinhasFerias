package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetRegisteredEventUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class GetRegisteredEventUiConverterTest {
    private val converter = GetRegisteredEventUiConverter()

    @Test
    fun testConvertSuccess() {
        val itemMock = mock<RegisteredEvent>()
        val response = GetRegisteredEventUseCase.Response(itemMock)
        val expectedResult = itemMock

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}