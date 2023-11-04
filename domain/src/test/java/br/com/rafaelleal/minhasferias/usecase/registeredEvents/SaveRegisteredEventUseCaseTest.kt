package br.com.rafaelleal.minhasferias.usecase.registeredEvents

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

class SaveRegisteredEventUseCaseTest {

    private val registeredEventsRepository = mock<RegisteredEventsRepository>()

    private val useCase = SaveRegisteredEventUseCase(
        mock(),
        registeredEventsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val itemMock = mock<RegisteredEvent>()
        val expected = SaveRegisteredEventUseCase.Response
        val request = SaveRegisteredEventUseCase.Request(itemMock)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}