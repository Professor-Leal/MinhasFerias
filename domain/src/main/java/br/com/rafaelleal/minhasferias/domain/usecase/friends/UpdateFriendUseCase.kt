package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateFriendUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val friendsRepository: FriendsRepository
) : UseCase<
        UpdateFriendUseCase.Request,
        UpdateFriendUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> =
        friendsRepository.updateFriend(request.friend).map {
            Response(it)
        }

    data class Request(val friend: Friend) : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}