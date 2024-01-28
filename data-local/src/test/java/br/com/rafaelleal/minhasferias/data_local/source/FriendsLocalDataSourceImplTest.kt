package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.db.friends.dao.FriendDao
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

class FriendsLocalDataSourceImplTest {

    private val dao = mock<FriendDao>()
    private val dataSource = FriendsLocalDataSourceImpl(dao)

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
    fun getAllFriends(): Unit = runBlocking {
        val list = listOf(friend01)
        val listEntity = listOf(friendEntity01)
        whenever(dao.getAll()).thenReturn(flowOf(listEntity))
        val result = dataSource.getAllFriends().single()
        val expected = list
        assertEquals(expected, result)
        verify(dao).getAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun saveFriend(): Unit = runBlocking {
        val item = friend01
        val itemEntity = friendEntity01
        dataSource.saveFriend(item)
        verify(dao).save(itemEntity.copy(id = null))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getFriend(): Unit = runBlocking {
        val id = 1L
        val event = friend01
        val eventEntity = friendEntity01

        whenever(dao.getFriend(id)).thenReturn(flowOf(eventEntity))
        val result = dataSource.getFriend(id).single()
        val expected = event
        assertEquals(expected, result)
        verify(dao).getFriend(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateFriend(): Unit = runBlocking {
        val item = friend01
        val itemEntity = friendEntity01
        whenever(dao.updateFriend(itemEntity)).thenReturn(1)
        val result = dataSource.updateFriend(item).single()
        val expected = true
        assertEquals(expected, result)
        verify(dao).updateFriend(itemEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateFriendFail(): Unit = runBlocking {
        val item = friend01
        val itemEntity = friendEntity01
        whenever(dao.updateFriend(itemEntity)).thenReturn(0)
        val result = dataSource.updateFriend(item).single()
        val expected = false
        assertEquals(expected, result)
        verify(dao).updateFriend(itemEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteFriend(): Unit = runBlocking {
        val id = 1L
        whenever(dao.deleteFriend(id)).thenReturn(1)
        val result = dataSource.deleteFriend(id).single()
        val expected = true
        assertEquals(expected, result)
        verify(dao).deleteFriend(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteFriendFail(): Unit = runBlocking {
        val id = 1L
        whenever(dao.deleteFriend(id)).thenReturn(0)
        val result = dataSource.deleteFriend(id).single()
        val expected = false
        assertEquals(expected, result)
        verify(dao).deleteFriend(id)
    }


}


