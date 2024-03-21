package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.db.events_friends.dao.EventFriendDao
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities.EventFriendRelationshipEntity
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class EventFriendLocalDataSourceImplTest {

    private val dao = mock<EventFriendDao>()
    private val dataSource = EventFriendLocalDataSourceImpl(dao)

    val friend01 = Friend(
        name = "Name",
        surname = "Surname",
        phoneNumber = "555-0000",
        documentNumber = "123.123.123-23",
        id = 1L
    )
    val friendEntity01 = FriendEntity(
        name = "Name",
        surname = "Surname",
        phoneNumber = "555-0000",
        documentNumber = "123.123.123-23",
        id = 1L
    )

    @ExperimentalCoroutinesApi
    @Test
    fun getFriendsFromEvent(): Unit = runBlocking {
        val eventId = 1L
        val list = listOf(friend01)
        val listEntity = listOf(friendEntity01)
        whenever(dao.getFriendsFromEvent(eventId)).thenReturn(flowOf(listEntity))
        val result = dataSource.getFriendsFromEvent(eventId).single()
        val expected = list
        assertEquals(expected, result)
        verify(dao).getFriendsFromEvent(eventId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should save relationship if there is not when asked to change`(): Unit = runBlocking {
        val eventId = 1L
        val friendId = 2L

        whenever(dao.getByEventIdAndFriendId(eventId, friendId)).thenReturn(null)

        val result = dataSource.changeEventFriendRelationship(eventId, friendId).single()
        val expected = true

        assertEquals(expected, result)

        verify(dao).save(EventFriendRelationshipEntity(eventId, friendId))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should delete relationship if exists when asked to change`(): Unit = runBlocking {
        val eventId = 1L
        val friendId = 2L
        val id = 3L
        val eventFriendRelationshipEntity = EventFriendRelationshipEntity(eventId, friendId, id)

        whenever(dao.getByEventIdAndFriendId(eventId, friendId)).thenReturn(
            eventFriendRelationshipEntity
        )

        val result = dataSource.changeEventFriendRelationship(eventId, friendId).single()
        val expected = true

        assertEquals(expected, result)

        verify(dao).delete(id)
    }

}