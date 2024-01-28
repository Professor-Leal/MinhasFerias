package br.com.rafaelleal.minhasferias.data_repository.repositories

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

class FriendsRepositoryImplTest{
    private val localFriendsDataSource = mock<FriendsLocalDataSource>()
    private val repositoryImpl = FriendsRepositoryImpl(
        localFriendsDataSource
    )

    @ExperimentalCoroutinesApi
    @Test
    fun getAllFriends(): Unit = runBlocking {
        val listMock = mock<List<Friend>>()
        whenever(localFriendsDataSource.getAllFriends())
            .thenReturn(flowOf(listMock))
        val expected = listMock
        val response = repositoryImpl.getAllFriends().single()
        assertEquals(expected, response)
        verify(localFriendsDataSource).getAllFriends()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun saveFriend(): Unit = runBlocking {
        val itemMock = mock<Friend>()
        repositoryImpl.saveFriend(itemMock)
        verify(localFriendsDataSource).saveFriend(itemMock)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getFriend(): Unit = runBlocking {
        val id = 1L
        val eventMock = mock<Friend>()
        whenever(localFriendsDataSource.getFriend(id))
            .thenReturn(flowOf(eventMock))
        val expected = eventMock
        val response = repositoryImpl.getFriend(id).single()
        assertEquals(expected, response)
        verify(localFriendsDataSource).getFriend(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateFriend(): Unit = runBlocking {
        val eventMock = mock<Friend>()
        val fowOfTrue = flowOf(true)
        whenever(localFriendsDataSource.updateFriend(eventMock))
            .thenReturn(fowOfTrue)
        val expected = true
        val response = repositoryImpl.updateFriend(eventMock).single()
        assertEquals(expected, response)
        verify(localFriendsDataSource).updateFriend(eventMock)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteFriend(): Unit = runBlocking {
        val id = 1L
        val fowOfTrue = flowOf(true)
        whenever(localFriendsDataSource.deleteFriend(id))
            .thenReturn(fowOfTrue)
        val expected = true
        val response = repositoryImpl.deleteFriend(id).single()
        assertEquals(expected, response)
        verify(localFriendsDataSource).deleteFriend(id)
    }


}