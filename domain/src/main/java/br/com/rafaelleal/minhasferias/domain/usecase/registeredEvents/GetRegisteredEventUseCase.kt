package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRegisteredEventUseCase  @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: RegisteredEventsRepository
) : UseCase<
        GetRegisteredEventUseCase.Request,
        GetRegisteredEventUseCase.Response
        >(configuration)
{
    override fun process(
        request: Request
    ): Flow<Response> =
        registeredEventsRepository.getRegisteredEvent(request.id)
            .map { _event ->
                Response(_event)
            }

    data class Request(val id: Long) :  UseCase.Request
    data class Response(val event: RegisteredEvent) : UseCase.Response
}