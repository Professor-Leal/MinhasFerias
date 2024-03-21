package br.com.rafaelleal.minhasferias.data_repository.repositories

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.EventFriendLocalDataSource
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.FriendsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class EventFriendRepositoryImplTest{
    private val eventFriendLocalDataSource = mock<EventFriendLocalDataSource>()

    private val repositoryImpl = EventFriendRepositoryImpl(
        eventFriendLocalDataSource
    )

    @ExperimentalCoroutinesApi
    @Test
    fun getFriendsFromEvent(): Unit = runBlocking {

        val eventId = 1L
        val listMock = mock<List<Friend>>()
        whenever(eventFriendLocalDataSource.getFriendsFromEvent(eventId))
            .thenReturn(flowOf(listMock))
        val expected = listMock
        val response = repositoryImpl.getFriendsFromEvent(eventId).single()
        assertEquals(expected, response)
        verify(eventFriendLocalDataSource).getFriendsFromEvent(eventId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun changeEventFriendRelationship(): Unit = runBlocking {

        val eventId = 1L
        val friendId = 2L

        whenever(eventFriendLocalDataSource.changeEventFriendRelationship(eventId, friendId))
            .thenReturn(flowOf(true))
        val expected = true
        val response = repositoryImpl.changeEventFriendRelationship(eventId, friendId).single()
        assertEquals(expected, response)
        verify(eventFriendLocalDataSource).changeEventFriendRelationship(eventId, friendId)
    }
}
