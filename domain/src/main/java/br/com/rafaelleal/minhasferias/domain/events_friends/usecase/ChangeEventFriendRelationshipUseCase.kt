package br.com.rafaelleal.minhasferias.domain.events_friends.usecase

import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeEventFriendRelationshipUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val eventFriendRepository: EventFriendRepository,
) : UseCase<
        ChangeEventFriendRelationshipUseCase.Request,
        ChangeEventFriendRelationshipUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response>  =
        eventFriendRepository.changeEventFriendRelationship(request.eventId, request.friendId)
            .map {
                Response(it)
            }

    data class Request(val eventId: Long, val friendId: Long) : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}