package br.com.rafaelleal.minhasferias.presentation_events_friends.converters

import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.GetRegisteredEventWithFriendsUseCase
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventWithFriendsUiModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import java.time.LocalDateTime

class GetRegisteredEventWithFriendsConverterTest {
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

    val friend03Mock = Friend(
        id = 3L,
        name = "John",
        surname = "Travolta",
        phoneNumber = "11111-1113",
        documentNumber = "111.111.111-13"
    )
    val friend04Mock = Friend(
        id = 4L,
        name = "Deny",
        surname = "Devito",
        phoneNumber = "11111-1114",
        documentNumber = "111.111.111-14"
    )

    val friend05Mock = Friend(
        id = 5L,
        name = "Keanu",
        surname = "Reeves",
        phoneNumber = "11111-1115",
        documentNumber = "111.111.111-15"
    )

    val friendListMock = listOf(
        friend01Mock,
        friend02Mock,
        friend03Mock,
        friend04Mock,
        friend05Mock
    ).sortedBy { it.name }

    val chosenFriends = listOf(
        friend03Mock,
        friend04Mock,
        friend05Mock
    ).sortedBy { it.name }

    val event01 = RegisteredEvent(
        name = "Evento dos artistas",
        address = "Hollywood",
        time = "23:00",
        day = "30/12/2024",
        date = LocalDateTime.of(2024, 12, 30, 23, 0),
        id = 1L
    )

    private val converter = GetRegisteredEventWithFriendsConverter()

    @Test
    fun testConvertSuccess() {
        val data = GetRegisteredEventWithFriendsUseCase.Response(
            event = event01,
            listOfFriends = chosenFriends,
            allFriends = friendListMock
        )
        val expectedResult = EventWithFriendsUiModel(
            eventId = data.event.id,
            eventName = data.event.name,
            eventTime = "${data.event.day} - ${data.event.time}",
            friendList = listOf(
                EventFriendListItem(
                    id = friend01Mock.id,
                    name = "${friend01Mock.name} ${friend01Mock.surname}",
                    selected = false
                ),
                EventFriendListItem(
                    id = friend02Mock.id,
                    name = "${friend02Mock.name} ${friend02Mock.surname}",
                    selected = false
                ),
                EventFriendListItem(
                    id = friend03Mock.id,
                    name = "${friend03Mock.name} ${friend03Mock.surname}",
                    selected = true
                ),
                EventFriendListItem(
                    id = friend04Mock.id,
                    name = "${friend04Mock.name} ${friend04Mock.surname}",
                    selected = true
                ),
                EventFriendListItem(
                    id = friend05Mock.id,
                    name = "${friend05Mock.name} ${friend05Mock.surname}",
                    selected = true
                ),
            ).sortedBy { it.name }
        )

        val convertResult = converter.convertSuccess(data)
        assertEquals(expectedResult, convertResult)
    }

}