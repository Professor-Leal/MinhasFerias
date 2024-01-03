package br.com.rafaelleal.minhasferias.presentation_registered_events.converters

import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.DeleteRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class DeleteRegisteredEventUiConverter @Inject constructor() :
    CommonResultConverter<DeleteRegisteredEventUseCase.Response, Boolean>() {
    override fun convertSuccess(
        data: DeleteRegisteredEventUseCase.Response
    ): Boolean {
        return data.result
    }
}