package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class SaveFriendUseCaseTest{

    private val friendsRepository = mock<FriendsRepository>()

    private val useCase = SaveFriendUseCase(
        mock(),
        friendsRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val itemMock = mock<Friend>()
        val expected = SaveFriendUseCase.Response
        val request = SaveFriendUseCase.Request(itemMock)
        val response = useCase.process(request).single()
        assertEquals(expected, response)
    }
}