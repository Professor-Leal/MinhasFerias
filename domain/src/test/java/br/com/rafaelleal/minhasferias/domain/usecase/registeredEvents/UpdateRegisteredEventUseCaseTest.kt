package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UpdateRegisteredEventUseCaseTest {

    private val registeredEventsRepository = mock<RegisteredEventsRepository>()

    private val useCase = UpdateRegisteredEventUseCase(
        mock(),
        registeredEventsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val itemMock = mock<RegisteredEvent>()
        val flowOfTrue = flowOf(true)
        whenever(registeredEventsRepository.updateRegisteredEvent(itemMock)).thenReturn(flowOfTrue)
        val expected = UpdateRegisteredEventUseCase.Response(true)
        val request = UpdateRegisteredEventUseCase.Request(itemMock)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}