package br.com.rafaelleal.minhasferias.app.mocks

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.sealed.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class MockDb {
    companion object {
        var registeredEventId = 1L
        var registeredEventList = mutableListOf<RegisteredEvent>()
        private val _resgisteredEventsListFlow = MutableStateFlow<List<RegisteredEvent>>(listOf())

        fun clearAll() {
            registeredEventId = 1L
            registeredEventList = mutableListOf<RegisteredEvent>()
        }

        fun getAllRegisteredEvents(): StateFlow<List<RegisteredEvent>> = _resgisteredEventsListFlow

        fun addRegisteredEvent(registeredEvent: RegisteredEvent) {
            registeredEventList.add(registeredEvent.copy(id = registeredEventId++))
            _resgisteredEventsListFlow.value = registeredEventList
        }

        fun addTwoRegisteredEventsToDB() {
            registeredEventList = mutableListOf<RegisteredEvent>(
                RegisteredEvent(
                    name = "name 1",
                    address = "address 1",
                    time = "12:05",
                    day = "01/02/2023",
                    id = 1L
                ),
                RegisteredEvent(
                    name = "name 2",
                    address = "address 2",
                    time = "12:37",
                    day = "01/02/2023 ",
                    id = 2L
                )
            )
            _resgisteredEventsListFlow.value = registeredEventList
        }

        fun getRegisteredEvent(id: Long): Flow<RegisteredEvent> = flow{
            _resgisteredEventsListFlow.value.filter{
                it.id == id
            }.firstOrNull()?.let { emit(it) } ?: throw UseCaseException.RegisteredEventException(
                Throwable("RegisteredEvent of id = $id not found")
            )
        }

        fun updateRegisteredEvent(registeredEvent: RegisteredEvent): Flow<Boolean> = flow {
            val actualList = _resgisteredEventsListFlow.value.toMutableList()
            val filteredList = actualList.filter{ it.id == registeredEvent.id }
            if (filteredList.isNullOrEmpty() ){
                emit(false)
            } else {
                actualList.remove(filteredList.get(0))
                actualList.add(registeredEvent)
                _resgisteredEventsListFlow.value = actualList.sortedBy { it.id }
                emit(true)
            }
        }

        fun deleteRegisteredEvent(id: Long): Flow<Boolean> = flow {
            val actualList = _resgisteredEventsListFlow.value.toMutableList()
            val filteredList = actualList.filter{ it.id == id }
            if (filteredList.isNullOrEmpty() ){
                emit(false)
            } else {
                actualList.remove(filteredList.get(0))
                emit(true)
            }
        }
    }
}