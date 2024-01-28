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

class GetFriendUseCaseTest{

    private val friendsRepository = mock<FriendsRepository>()

    private val useCase = GetFriendUseCase(
        mock(),
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val id = 1L
        val eventMock = mock<Friend>()
        whenever(friendsRepository.getFriend(id)).thenReturn(flowOf(eventMock))
        val expected = GetFriendUseCase.Response(eventMock)
        val request = GetFriendUseCase.Request(id)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}
