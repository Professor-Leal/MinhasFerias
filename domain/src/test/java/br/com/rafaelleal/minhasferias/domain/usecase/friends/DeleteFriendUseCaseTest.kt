package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DeleteFriendUseCaseTest{

    private val friendsRepository = mock<FriendsRepository>()

    private val useCase = DeleteFriendUseCase(
        mock(),
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val id = 1L
        val flowOfTrue = flowOf(true)
        whenever(friendsRepository.deleteFriend(id)).thenReturn(flowOfTrue)
        val expected = DeleteFriendUseCase.Response(true)
        val request = DeleteFriendUseCase.Request(id)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}