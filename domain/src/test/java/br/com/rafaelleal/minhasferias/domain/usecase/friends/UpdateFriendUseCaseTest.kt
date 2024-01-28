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

class UpdateFriendUseCaseTest{
    private val friendsRepository = mock<FriendsRepository>()

    private val useCase = UpdateFriendUseCase(
        mock(),
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val itemMock = mock<Friend>()
        val flowOfTrue = flowOf(true)
        whenever(friendsRepository.updateFriend(itemMock)).thenReturn(flowOfTrue)
        val expected = UpdateFriendUseCase.Response(true)
        val request = UpdateFriendUseCase.Request(itemMock)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}