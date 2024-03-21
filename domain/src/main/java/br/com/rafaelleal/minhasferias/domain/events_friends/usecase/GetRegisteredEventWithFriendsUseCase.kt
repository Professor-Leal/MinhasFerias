package br.com.rafaelleal.minhasferias.domain.events_friends.usecase

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetRegisteredEventWithFriendsUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: RegisteredEventsRepository,
    private val eventFriendRepository: EventFriendRepository,
    private val friendsRepository: FriendsRepository
) : UseCase<
        GetRegisteredEventWithFriendsUseCase.Request,
        GetRegisteredEventWithFriendsUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> {
        return combine(
            registeredEventsRepository.getRegisteredEvent(request.eventId),
            eventFriendRepository.getFriendsFromEvent(request.eventId),
            friendsRepository.getAllFriends()
        ) { _event, _eventFriends, _allFriends ->
            Response(event = _event, listOfFriends = _eventFriends, allFriends = _allFriends)
        }
    }

    data class Request(val eventId: Long) : UseCase.Request
    data class Response(
        val event: RegisteredEvent,
        val listOfFriends: List<Friend>,
        val allFriends: List<Friend>
    ) : UseCase.Response
}