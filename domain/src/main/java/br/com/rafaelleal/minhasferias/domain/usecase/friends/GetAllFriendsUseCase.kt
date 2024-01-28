package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetAllFriendsUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val friendsRepository: FriendsRepository
) : UseCase<
        GetAllFriendsUseCase.Request,
        GetAllFriendsUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> =
        friendsRepository.getAllFriends()
            .map { _list ->
                Response(_list)
            }

    object Request : UseCase.Request
    data class Response(val list: List<Friend>) : UseCase.Response
}