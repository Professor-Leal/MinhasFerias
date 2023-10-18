package br.com.rafaelleal.minhasferias.presentation_registered_events.list.converters

import android.content.Context
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.R
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAllRegisteredEventsUiConverter @Inject constructor(
    @ApplicationContext private val context: Context
) :
    CommonResultConverter<GetAllRegisteredEventsUseCase.Response, RegisteredEventsListModel>() {

    override fun convertSuccess(data: GetAllRegisteredEventsUseCase.Response): RegisteredEventsListModel {
        return RegisteredEventsListModel(
            headerText = context.getString(
                R.string.registered_events_list_header,
            ),
            items = data.list.map { _event ->
                RegisteredEventListItemModel(
                    name = _event.name,
                    address = _event.address,
                    dayTime = "${_event.day} - ${_event.time}",
                    id = _event.id
                )
            }
        )
    }
}