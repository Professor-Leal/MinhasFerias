package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
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
import java.time.LocalDateTime

class RegisteredEventsLocalDataSourceImplTest {

    private val dao = mock<RegisteredEventDao>()
    private val dataSource = RegisteredEventsLocalDataSourceImpl(dao)

    @ExperimentalCoroutinesApi
    @Test
    fun getAllRegisteredEvents(): Unit = runBlocking {
        val list = listOf(
            RegisteredEvent(
                "Evento 01",
                "Endereço",
                "12:00",
                "01/01/2023",
                LocalDateTime.of(2023, 1, 1, 12, 0),
                1
            )
        )
        val listEntity = listOf(
            RegisteredEventEntity(
                "Evento 01",
                "Endereço",
                "12:00",
                "01/01/2023",
                LocalDateTime.of(2023, 1, 1, 12, 0),
                1
            )
        )
        whenever(dao.getAll()).thenReturn(flowOf(listEntity))
        val result = dataSource.getAllRegisteredEvents().single()
        val expected = list
        assertEquals(expected, result)
        verify(dao).getAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun saveRegisteredEvent(): Unit = runBlocking {
        val item = RegisteredEvent(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            1
        )
        val itemEntity = RegisteredEventEntity(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            1
        )
        dataSource.saveRegisteredEvent(item)
        verify(dao).save(itemEntity.copy(id = null))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRegisteredEvent(): Unit = runBlocking {
        val id = 1L
        val event = RegisteredEvent(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            id
        )
        val eventEntity =
            RegisteredEventEntity(
                "Evento 01",
                "Endereço",
                "12:00",
                "01/01/2023",
                LocalDateTime.of(2023, 1, 1, 12, 0),
                id
            )

        whenever(dao.getRegisteredEvent(id)).thenReturn(flowOf(eventEntity))
        val result = dataSource.getRegisteredEvent(id).single()
        val expected = event
        assertEquals(expected, result)
        verify(dao).getRegisteredEvent(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateRegisteredEvent(): Unit = runBlocking {
        val item = RegisteredEvent(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            1
        )
        val itemEntity = RegisteredEventEntity(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            1
        )
        whenever(dao.updateRegisteredEvent(itemEntity)).thenReturn(1)
        val result = dataSource.updateRegisteredEvent(item).single()
        val expected = true
        assertEquals(expected, result)
        verify(dao).updateRegisteredEvent(itemEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateRegisteredEventFail(): Unit = runBlocking {
        val item = RegisteredEvent(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            1
        )
        val itemEntity = RegisteredEventEntity(
            "Evento 01",
            "Endereço",
            "12:00",
            "01/01/2023",
            LocalDateTime.of(2023, 1, 1, 12, 0),
            1
        )
        whenever(dao.updateRegisteredEvent(itemEntity)).thenReturn(0)
        val result = dataSource.updateRegisteredEvent(item).single()
        val expected = false
        assertEquals(expected, result)
        verify(dao).updateRegisteredEvent(itemEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteRegisteredEvent(): Unit = runBlocking {
        val id = 1L
        whenever(dao.deleteRegisteredEvent(id)).thenReturn(1)
        val result = dataSource.deleteRegisteredEvent(id).single()
        val expected = true
        assertEquals(expected, result)
        verify(dao).deleteRegisteredEvent(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteRegisteredEventFail(): Unit = runBlocking {
        val id = 1L
        whenever(dao.deleteRegisteredEvent(id)).thenReturn(0)
        val result = dataSource.deleteRegisteredEvent(id).single()
        val expected = false
        assertEquals(expected, result)
        verify(dao).deleteRegisteredEvent(id)
    }


}


