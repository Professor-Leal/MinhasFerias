package br.com.rafaelleal.minhasferias.presentation_events_friends.converters

import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.GetRegisteredEventWithFriendsUseCase
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.usecase.friends.SearchFriendsByNameUseCase
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import org.junit.Assert.*
import org.junit.Test

class SearchFriendsByNameConverterTest {

    val friend01Mock = Friend(
        id = 1L,
        name = "John",
        surname = "Doe",
        phoneNumber = "11111-1111",
        documentNumber = "111.111.111-11"
    )

    val friend02Mock = Friend(
        id = 2L,
        name = "Jane",
        surname = "Doe",
        phoneNumber = "11111-1112",
        documentNumber = "111.111.111-12"
    )
    private val converter = SearchFriendsByNameConverter()

    @Test
    fun testConvertSuccess() {
        val data = SearchFriendsByNameUseCase.Response(
            listOfFriends = listOf(friend01Mock), allFriends = listOf(friend01Mock, friend02Mock)
        )
        val expectedResult = listOf(
            EventFriendListItem(
                id = friend01Mock.id,
                name = "${friend01Mock.name} ${friend01Mock.surname}",
                selected = true
            ),
            EventFriendListItem(
                id = friend02Mock.id,
                name = "${friend02Mock.name} ${friend02Mock.surname}",
                selected = false
            )
        ).sortedBy { it.name }
        val convertResult = converter.convertSuccess(data)
        assertEquals(expectedResult, convertResult)
    }
}