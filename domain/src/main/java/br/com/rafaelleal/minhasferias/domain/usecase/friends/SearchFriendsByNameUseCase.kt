package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchFriendsByNameUseCase  @Inject constructor(
    configuration: UseCase.Configuration,
    private val friendsRepository: FriendsRepository
) : UseCase<
        SearchFriendsByNameUseCase.Request,
        SearchFriendsByNameUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> =
        friendsRepository.searchFriendsByName(request.searchInput)
            .map { _list ->
                Response(_list)
            }

    data class Request(val searchInput: String) : UseCase.Request
    data class Response(val list: List<Friend>) : UseCase.Response
}