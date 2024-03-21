package br.com.rafaelleal.minhasferias.domain.events_friends.usecase

import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ChangeEventFriendRelationshipUseCaseTest {
    private val eventFriendRepository = mock<EventFriendRepository>()

    private val useCase = ChangeEventFriendRelationshipUseCase(
        mock(),
        eventFriendRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlocking {
        val eventId = 1L
        val friendId = 2L

        whenever(eventFriendRepository.changeEventFriendRelationship(eventId, friendId)).thenReturn(flowOf(true))

        val expected =
            ChangeEventFriendRelationshipUseCase.Response(true)
        val request = ChangeEventFriendRelationshipUseCase.Request(eventId = eventId, friendId = friendId)
        val response = useCase.process(request).single()
        Assert.assertEquals(expected, response)
    }

}