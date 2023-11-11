package br.com.rafaelleal.minhasferias.app.mocks

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockDb {
    companion object {
        var id = 1L

        var registeredEventList = mutableListOf<RegisteredEvent>()

        private val _resgisteredEventsListFlow = MutableStateFlow<List<RegisteredEvent>>(listOf())
        fun getAllRegisteredEvents(): StateFlow<List<RegisteredEvent>> = _resgisteredEventsListFlow

        fun addRegisteredEvent(registeredEvent: RegisteredEvent) {
            registeredEventList.add(registeredEvent.copy(id = id++))
            _resgisteredEventsListFlow.value = registeredEventList
        }

        fun clearAll() {
            id = 1L
            registeredEventList = mutableListOf<RegisteredEvent>()
        }


        fun addTwoRegisteredEventsToDB() {
            registeredEventList = mutableListOf<RegisteredEvent>(
                RegisteredEvent(
                    name = "name 1",
                    address = "address 1",
                    time = "time",
                    day = "day",
                    id = 1L
                ),
                RegisteredEvent(
                    name = "name 2",
                    address = "address 2",
                    time = "time 2 ",
                    day = "day 2 ",
                    id = 2L
                )
            )
            _resgisteredEventsListFlow.value = registeredEventList
        }
    }
}