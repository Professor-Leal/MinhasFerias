package br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents

import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllRegisteredEventsUseCase  @Inject constructor(
    configuration: UseCase.Configuration,
    private val registeredEventsRepository: RegisteredEventsRepository
) : UseCase<
        GetAllRegisteredEventsUseCase.Request,
        GetAllRegisteredEventsUseCase.Response
        >(configuration)
{
    override fun process(
        request: Request
    ): Flow<Response> =
        registeredEventsRepository.getAllRegisteredEvents()
            .map { _list ->
                Response(_list)
            }

    object Request : UseCase.Request
    data class Response(val list: List<RegisteredEvent>) : UseCase.Response
}