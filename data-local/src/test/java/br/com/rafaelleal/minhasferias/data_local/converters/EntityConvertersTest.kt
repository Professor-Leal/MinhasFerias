package br.com.rafaelleal.minhasferias.data_local.converters

import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import br.com.rafaelleal.minhasferias.domain.models.Friend
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

class EntityConvertersTest {

    @Test
    fun registeredEventToModel() {

        val registeredEventEntity = RegisteredEventEntity(
            id = 2L,
            name = "nome Evento",
            address = "Endereço do local",
            time = "12:00",
            day = "01/01/2023",
            date = LocalDateTime.of(2023, 1, 1, 12, 0)
        )

        val registeredEvent = registeredEventEntity.toModel()
        assertEquals(registeredEvent.id, registeredEventEntity.id)
        assertEquals(registeredEvent.name, registeredEventEntity.name)
        assertEquals(registeredEvent.address, registeredEventEntity.address)
        assertEquals(registeredEvent.time, registeredEventEntity.time)
        assertEquals(registeredEvent.day, registeredEventEntity.day)
        assertEquals(registeredEvent.date, registeredEventEntity.date)
        assert(registeredEvent is RegisteredEvent)
    }

    @Test
    fun registeredEventToEntity() {
        val registeredEvent = RegisteredEvent(
            id = 2L,
            name = "nome Evento",
            address = "Endereço do local",
            time = "12:00",
            day = "01/01/2023",
            date = LocalDateTime.of(2023, 1, 1, 12, 0)
        )

        val registeredEventEntity = registeredEvent.toEntity()
        assertEquals(registeredEvent.id, registeredEventEntity.id)
        assertEquals(registeredEvent.name, registeredEventEntity.name)
        assertEquals(registeredEvent.address, registeredEventEntity.address)
        assertEquals(registeredEvent.time, registeredEventEntity.time)
        assertEquals(registeredEvent.day, registeredEventEntity.day)
        assertEquals(registeredEvent.date, registeredEventEntity.date)
        assert(registeredEventEntity is RegisteredEventEntity)

    }

    @Test
    fun friendToModel() {

        val friendEntity = FriendEntity(
            name = "John",
            surname = "Doe",
            phoneNumber = "555-0001",
            documentNumber = "111.111.111-11",
            id = 1
        )

        val friend = friendEntity.toModel()
        assertEquals(friend.id, friendEntity.id)
        assertEquals(friend.name, friendEntity.name)
        assertEquals(friend.surname, friendEntity.surname)
        assertEquals(friend.phoneNumber, friendEntity.phoneNumber)
        assertEquals(friend.documentNumber, friendEntity.documentNumber)
        assert(friend is Friend)
    }

    @Test
    fun friendToEntity() {
        val friend = Friend(
            name = "John",
            surname = "Doe",
            phoneNumber = "555-0001",
            documentNumber = "111.111.111-11",
            id = 1
        )

        val friendEntity = friend.toEntity()
        assertEquals(friend.id, friendEntity.id)
        assertEquals(friend.name, friendEntity.name)
        assertEquals(friend.surname, friendEntity.surname)
        assertEquals(friend.phoneNumber, friendEntity.phoneNumber)
        assertEquals(friend.documentNumber, friendEntity.documentNumber)
        assert(friendEntity is FriendEntity)

    }

}