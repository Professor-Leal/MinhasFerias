package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisteredEventsLocalDataSourceImplTest {

    private val dao = mock<RegisteredEventDao>()
    private val dataSource = RegisteredEventsLocalDataSourceImpl(dao)
    @ExperimentalCoroutinesApi
    @Test
    fun getAllRegisteredEvents(): Unit = runBlocking {
        val list = listOf(
            RegisteredEvent( "Evento 01", "Endereço", "12:00", "01/01/2023", 1)
        )
        val listEntity = listOf(
            RegisteredEventEntity( "Evento 01", "Endereço", "12:00", "01/01/2023",1 )
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
        val item  = RegisteredEvent( "Evento 01", "Endereço", "12:00", "01/01/2023", 1)
        val itemEntity  = RegisteredEventEntity( "Evento 01", "Endereço", "12:00", "01/01/2023",1 )
        dataSource.saveRegisteredEvent(item)
        verify(dao).save(itemEntity)
    }

}


