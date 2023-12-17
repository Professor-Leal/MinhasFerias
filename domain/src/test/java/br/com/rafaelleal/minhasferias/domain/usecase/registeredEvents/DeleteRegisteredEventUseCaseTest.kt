package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DeleteRegisteredEventUseCaseTest {

    private val registeredEventsRepository = mock<RegisteredEventsRepository>()

    private val useCase = DeleteRegisteredEventUseCase(
        mock(),
        registeredEventsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val id = 1L
        val flowOfTrue = flowOf(true)
        whenever(registeredEventsRepository.deleteRegisteredEvent(id)).thenReturn(flowOfTrue)
        val expected = DeleteRegisteredEventUseCase.Response(true)
        val request = DeleteRegisteredEventUseCase.Request(id)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}