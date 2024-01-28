package br.com.rafaelleal.minhasferias.domain.usecase.friends

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFriendUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: FriendsRepository
) : UseCase<
        GetFriendUseCase.Request,
        GetFriendUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> =
        registeredEventsRepository.getFriend(request.id)
            .map { _event ->
                Response(_event)
            }

    data class Request(val id: Long) : UseCase.Request
    data class Response(val event: Friend) : UseCase.Response
}