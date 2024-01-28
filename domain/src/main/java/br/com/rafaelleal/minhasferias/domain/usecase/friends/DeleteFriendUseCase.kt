package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val friendsRepository: FriendsRepository
) : UseCase<
        DeleteFriendUseCase.Request,
        DeleteFriendUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> =
        friendsRepository.deleteFriend(request.id)
            .map { _result ->
                Response(_result)
            }

    data class Request(val id: Long) : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}