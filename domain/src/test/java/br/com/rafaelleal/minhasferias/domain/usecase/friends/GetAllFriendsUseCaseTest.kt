package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllFriendsUseCaseTest{

    private val friendsRepository = mock<FriendsRepository>()

    private val useCase = GetAllFriendsUseCase(
        mock(),
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val listMock = mock<List<Friend>>()
        whenever(friendsRepository.getAllFriends()).thenReturn(flowOf(listMock))
        val expected = GetAllFriendsUseCase.Response(listMock)
        val request = GetAllFriendsUseCase.Request
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}