package br.com.rafaelleal.minhasferias.data_local.converters

import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

class EntityConvertersTest {

    @Test
    fun registeredEventToModel(){

        val registeredEventEntity = RegisteredEventEntity(
            id = 2L,
            name = "nome Evento",
            address = "Endereço do local",
            time = "12:00",
            day = "01/01/2023",
            date = LocalDateTime.of(2023,1,1,12,0)
        )

        val registeredEvent = registeredEventEntity.toModel()
        assertEquals(registeredEvent.id, registeredEventEntity.id)
        assertEquals(registeredEvent.name, registeredEventEntity.name)
        assertEquals(registeredEvent.address, registeredEventEntity.address)
        assertEquals(registeredEvent.time, registeredEventEntity.time)
        assertEquals(registeredEvent.day, registeredEventEntity.day)
        assertEquals(registeredEvent.date, registeredEventEntity.date)
        assert( registeredEvent is RegisteredEvent )
    }

    @Test
    fun registeredEventToEntity(){
        val registeredEvent = RegisteredEvent(
            id = 2L,
            name = "nome Evento",
            address = "Endereço do local",
            time = "12:00",
            day = "01/01/2023",
            date = LocalDateTime.of(2023,1,1,12,0)
        )

        val registeredEventEntity = registeredEvent.toEntity()
        assertEquals(registeredEvent.id, registeredEventEntity.id)
        assertEquals(registeredEvent.name, registeredEventEntity.name)
        assertEquals(registeredEvent.address, registeredEventEntity.address)
        assertEquals(registeredEvent.time, registeredEventEntity.time)
        assertEquals(registeredEvent.day, registeredEventEntity.day)
        assertEquals(registeredEvent.date, registeredEventEntity.date)
        assert( registeredEventEntity is RegisteredEventEntity )

    }
}