package br.com.rafaelleal.minhasferias.domain.events_friends.usecase

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class GetRegisteredEventWithFriendsUseCaseTest {

    private val registeredEventsRepository = mock<RegisteredEventsRepository>()
    private val eventFriendRepository = mock<EventFriendRepository>()
    private val friendsRepository = mock<FriendsRepository>()

    private val useCase = GetRegisteredEventWithFriendsUseCase(
        mock(),
        registeredEventsRepository,
        eventFriendRepository,
        friendsRepository
    )

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


    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val eventId = 1L

        whenever(registeredEventsRepository.getRegisteredEvent(eventId)).thenReturn(flowOf(event01))
        whenever(eventFriendRepository.getFriendsFromEvent(eventId)).thenReturn(flowOf(chosenFriends))
        whenever(friendsRepository.getAllFriends()).thenReturn(flowOf(friendListMock))

        val expected =
            GetRegisteredEventWithFriendsUseCase.Response(event01, chosenFriends, friendListMock)
        val request = GetRegisteredEventWithFriendsUseCase.Request(eventId = eventId)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}
