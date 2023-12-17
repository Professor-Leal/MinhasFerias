package br.com.rafaelleal.minhasferias.data_repository.repositories

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisteredEventsRepositoryImplTest {

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

    @ExperimentalCoroutinesApi
    @Test
    fun saveRegisteredEvent(): Unit = runBlocking {
        val itemMock = mock<RegisteredEvent>()
        repositoryImpl.saveRegisteredEvent(itemMock)
        verify(localRegisteredEventsDataSource).saveRegisteredEvent(itemMock)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRegisteredEvent(): Unit = runBlocking {
        val id = 1L
        val eventMock = mock<RegisteredEvent>()
        whenever(localRegisteredEventsDataSource.getRegisteredEvent(id))
            .thenReturn(flowOf(eventMock))
        val expected = eventMock
        val response = repositoryImpl.getRegisteredEvent(id).single()
        assertEquals(expected, response)
        verify(localRegisteredEventsDataSource).getRegisteredEvent(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateRegisteredEvent(): Unit = runBlocking {
        val eventMock = mock<RegisteredEvent>()
        val fowOfTrue = flowOf(true)
        whenever(localRegisteredEventsDataSource.updateRegisteredEvent(eventMock))
            .thenReturn(fowOfTrue)
        val expected = true
        val response = repositoryImpl.updateRegisteredEvent(eventMock).single()
        assertEquals(expected, response)
        verify(localRegisteredEventsDataSource).updateRegisteredEvent(eventMock)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteRegisteredEvent(): Unit = runBlocking {
        val id = 1L
        val fowOfTrue = flowOf(true)
        whenever(localRegisteredEventsDataSource.deleteRegisteredEvent(id))
            .thenReturn(fowOfTrue)
        val expected = true
        val response = repositoryImpl.deleteRegisteredEvent(id).single()
        assertEquals(expected, response)
        verify(localRegisteredEventsDataSource).deleteRegisteredEvent(id)
    }


}