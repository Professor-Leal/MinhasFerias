package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
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

    private val useCase = SearchFriendsByNameUseCase(
        mock(),
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val searchInput = "Ped"
        val listMock = mock<List<Friend>>()
        whenever(friendsRepository.searchFriendsByName(searchInput)).thenReturn(flowOf(listMock))
        val expected = SearchFriendsByNameUseCase.Response(listMock)
        val request = SearchFriendsByNameUseCase.Request(searchInput)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}