package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveRegisteredEventUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: RegisteredEventsRepository
) : UseCase<
        SaveRegisteredEventUseCase.Request,
        SaveRegisteredEventUseCase.Response
        >(configuration) {
    override fun process(
        request: Request
    ): Flow<Response> = flow{
        registeredEventsRepository.saveRegisteredEvent(request.registeredEvent)
        emit(Response)
    }
    data class Request(val registeredEvent: RegisteredEvent) : UseCase.Request
    object Response : UseCase.Response
}