package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateRegisteredEventUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: RegisteredEventsRepository
) : UseCase<
        UpdateRegisteredEventUseCase.Request,
        UpdateRegisteredEventUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> =
        registeredEventsRepository.updateRegisteredEvent(request.registeredEvent).map {
            Response(it)
        }

    data class Request(val registeredEvent: RegisteredEvent) : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}