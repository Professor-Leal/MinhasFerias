package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.SaveRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject


class SaveRegisteredEventUiConverter @Inject constructor() :
    CommonResultConverter<SaveRegisteredEventUseCase.Response, Unit>() {
    override fun convertSuccess(
        data: SaveRegisteredEventUseCase.Response
    ): Unit {
        return Unit
    }
}