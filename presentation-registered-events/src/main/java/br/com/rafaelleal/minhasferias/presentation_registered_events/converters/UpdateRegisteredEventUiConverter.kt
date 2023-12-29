package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.UpdateRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class UpdateRegisteredEventUiConverter @Inject constructor() :
    CommonResultConverter<UpdateRegisteredEventUseCase.Response, Boolean>() {
    override fun convertSuccess(
        data: UpdateRegisteredEventUseCase.Response
    ): Boolean {
        return data.result
    }
}