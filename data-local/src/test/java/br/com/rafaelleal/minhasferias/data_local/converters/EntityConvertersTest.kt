package br.com.rafaelleal.minhasferias.data_local.converters

import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.entities.RegisteredEventEntity
import br.com.rafaelleal.minhasferias.models.RegisteredEvent
import org.junit.Assert.assertEquals
import org.junit.Test

class EntityConvertersTest {

    @Test
    fun registeredEventToModel(){
        val registeredEventEntity = RegisteredEventEntity(
            id = 2,
            name = "nome Evento",
            address = "Endereço do local",
            time = "12:00",
            day = "01/01/2023"
        )

        val registeredEvent = registeredEventEntity.toModel()
        assertEquals(registeredEvent.id, registeredEventEntity.id)
        assertEquals(registeredEvent.name, registeredEventEntity.name)
        assertEquals(registeredEvent.address, registeredEventEntity.address)
        assertEquals(registeredEvent.time, registeredEventEntity.time)
        assertEquals(registeredEvent.day, registeredEventEntity.day)
    }

    @Test
    fun registeredEventToEntity(){
        val registeredEvent = RegisteredEvent(
            id = 2,
            name = "nome Evento",
            address = "Endereço do local",
            time = "12:00",
            day = "01/01/2023"
        )

        val registeredEventEntity = registeredEvent.toEntity()
        assertEquals(registeredEvent.id, registeredEventEntity.id)
        assertEquals(registeredEvent.name, registeredEventEntity.name)
        assertEquals(registeredEvent.address, registeredEventEntity.address)
        assertEquals(registeredEvent.time, registeredEventEntity.time)
        assertEquals(registeredEvent.day, registeredEventEntity.day)
    }

}