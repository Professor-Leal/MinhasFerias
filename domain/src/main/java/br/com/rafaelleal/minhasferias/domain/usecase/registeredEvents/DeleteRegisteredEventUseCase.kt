package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteRegisteredEventUseCase  @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: RegisteredEventsRepository
) : UseCase<
        DeleteRegisteredEventUseCase.Request,
        DeleteRegisteredEventUseCase.Response
        >(configuration)
{
    override fun process(
        request: Request
    ): Flow<Response> =
        registeredEventsRepository.deleteRegisteredEvent(request.id)
            .map { _result ->
                Response(_result)
            }

    data class Request(val id: Long) :  UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}