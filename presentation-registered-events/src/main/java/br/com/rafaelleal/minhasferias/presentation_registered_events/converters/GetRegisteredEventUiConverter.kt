package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class GetRegisteredEventUiConverter @Inject constructor() :
    CommonResultConverter<GetRegisteredEventUseCase.Response, RegisteredEvent>() {
    override fun convertSuccess(
        data: GetRegisteredEventUseCase.Response
    ): RegisteredEvent {
        return data.event
    }
}