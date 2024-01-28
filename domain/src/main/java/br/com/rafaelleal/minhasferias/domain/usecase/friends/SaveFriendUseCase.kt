package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveFriendUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val friendsRepository: FriendsRepository
) : UseCase<
        SaveFriendUseCase.Request,
        SaveFriendUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> = flow {
        friendsRepository.saveFriend(request.friend)
        emit(Response)
    }

    data class Request(val friend: Friend) : UseCase.Request
    object Response : UseCase.Response
}