package br.com.rafaelleal.minhasferias.data_repository.repositories

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisteredEventsRepositoryImplTest{

    private val localRegisteredEventsDataSource = mock<RegisteredEventsLocalDataSource>()
    private val repositoryImpl = RegisteredEventsRepositoryImpl(
        localRegisteredEventsDataSource
    )

    @ExperimentalCoroutinesApi
    @Test
    fun getAllRegisteredEvents(): Unit = runBlocking {
        val listMock = mock<List<RegisteredEvent>>()
        whenever(localRegisteredEventsDataSource.getAllRegisteredEvents())
            .thenReturn(flowOf(listMock))
        val expected = listMock
        val response = repositoryImpl.getAllRegisteredEvents().single()
        assertEquals(expected, response)
        verify(localRegisteredEventsDataSource).getAllRegisteredEvents()
    }

}