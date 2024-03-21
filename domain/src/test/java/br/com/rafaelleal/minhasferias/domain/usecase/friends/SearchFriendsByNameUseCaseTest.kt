package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchFriendsByNameUseCaseTest {

    private val friendsRepository = mock<FriendsRepository>()

    private val eventFriendRepository = mock<EventFriendRepository>()

    private val useCase = SearchFriendsByNameUseCase(
        mock(),
        eventFriendRepository,
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val eventId= 1L
        val searchInput = "Ped"
        val listMock = mock<List<Friend>>()
        whenever(eventFriendRepository.getFriendsFromEvent(eventId)).thenReturn(flowOf(listMock))
        whenever(friendsRepository.searchFriendsByName(searchInput)).thenReturn(flowOf(listMock))
        val expected = SearchFriendsByNameUseCase.Response(listMock, listMock)
        val request = SearchFriendsByNameUseCase.Request(searchInput, eventId)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}