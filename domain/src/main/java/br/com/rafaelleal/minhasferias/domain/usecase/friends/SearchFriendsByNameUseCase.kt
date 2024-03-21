package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SearchFriendsByNameUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val eventFriendRepository: EventFriendRepository,
    private val friendsRepository: FriendsRepository
) : UseCase<
        SearchFriendsByNameUseCase.Request,
        SearchFriendsByNameUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response>  {
        return combine(
            eventFriendRepository.getFriendsFromEvent(request.eventId),
            friendsRepository.searchFriendsByName(request.searchInput)
        ) { _eventFriends, _allFriends ->
            SearchFriendsByNameUseCase.Response(
                listOfFriends = _eventFriends,
                allFriends = _allFriends
            )
        }
    }

    data class Request(val searchInput: String, val eventId: Long) : UseCase.Request
    data class Response(
        val listOfFriends: List<Friend>,
        val allFriends: List<Friend>
    ) : UseCase.Response
}