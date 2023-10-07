package br.com.rafaelleal.minhasferias.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllRegisteredEventsUseCaseTest {
    
    private val registeredEventsRepository = mock<RegisteredEventsRepository>()

    private val useCase = GetAllRegisteredEventsUseCase(
        mock(),
        registeredEventsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val listMock = mock<List<RegisteredEvent>>()
        whenever(registeredEventsRepository.getAllRegisteredEvents()).thenReturn(flowOf(listMock))
        val expected = GetAllRegisteredEventsUseCase.Response(listMock)
        val request = GetAllRegisteredEventsUseCase.Request
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}

