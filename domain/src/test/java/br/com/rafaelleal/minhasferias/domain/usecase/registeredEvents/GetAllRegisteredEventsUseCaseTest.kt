package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.domain.models.RegisteredEvent
import br.com.rafaelleal.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
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
        val registeredEvent1 = RegisteredEvent(
            name = "name_01",
            address = "address_01",
            time = "20:00",
            day = "01/01/2000"
        )
        val list = listOf(registeredEvent1)
        
        whenever(registeredEventsRepository.getAllRegisteredEvents()).thenReturn(flowOf(list))

        val expected = GetAllRegisteredEventsUseCase.Response(list)
        val request = GetAllRegisteredEventsUseCase.Request
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}

