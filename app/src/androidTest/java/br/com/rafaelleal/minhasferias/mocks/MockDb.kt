package br.com.rafaelleal.minhasferias.mocks

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent

class MockDb {
    companion object {
        var registeredEventList = mutableListOf<RegisteredEvent>()

        fun clearAll() {
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
        }
    }
}