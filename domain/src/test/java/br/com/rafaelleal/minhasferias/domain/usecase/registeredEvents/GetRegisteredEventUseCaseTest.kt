package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetRegisteredEventUseCaseTest {

    private val registeredEventsRepository = mock<RegisteredEventsRepository>()

    private val useCase = GetRegisteredEventUseCase(
        mock(),
        registeredEventsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val id = 1L
        val eventMock = mock<RegisteredEvent>()
        whenever(registeredEventsRepository.getRegisteredEvent(id)).thenReturn(flowOf(eventMock))
        val expected = GetRegisteredEventUseCase.Response(eventMock)
        val request = GetRegisteredEventUseCase.Request(id)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}
