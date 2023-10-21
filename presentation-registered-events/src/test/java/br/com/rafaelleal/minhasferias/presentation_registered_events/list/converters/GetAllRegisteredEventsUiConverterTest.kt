package br.com.rafaelleal.minhasferias.presentation_registered_events.list.converters

import android.content.Context
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import br.com.rafaelleal.minhasferias.presentation_registered_events.R
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllRegisteredEventsUiConverterTest {
    private val context = mock<Context>()
    private val converter = GetAllRegisteredEventsUiConverter(context)
    val listRegisteredEvent = listOf(
        RegisteredEvent(
            name = "name", address = "address", time = "time", day = "day", id = 1L
        )
    )
    val listItemsModel = listRegisteredEvent.map { _event ->
        RegisteredEventListItemModel(
name=_event.name, address=_event.address, dayTime="${_event.day} - ${_event.time}",id=_event.id
        )
    }

    @Test
    fun testConvertSuccess() {
        val response = GetAllRegisteredEventsUseCase.Response(listRegisteredEvent)
        val expectedResult = RegisteredEventsListModel(
            headerText = "Registered Events",
            items = listItemsModel
        )
        whenever(
            context.getString(R.string.registered_events_list_header)
        ).thenReturn("Registered Events")

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}