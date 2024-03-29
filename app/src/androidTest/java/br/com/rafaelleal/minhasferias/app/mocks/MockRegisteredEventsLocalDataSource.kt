package br.com.rafaelleal.minhasferias.app.mocks

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class MockRegisteredEventsLocalDataSource @Inject constructor() : RegisteredEventsLocalDataSource {
    override fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>> =
        MockDb.getAllRegisteredEvents()

    override fun saveRegisteredEvent(registeredEvent: RegisteredEvent) {
        MockDb.addRegisteredEvent(registeredEvent)
    }

    override fun getRegisteredEvent(id: Long): Flow<RegisteredEvent> =
        MockDb.getRegisteredEvent(id)

    override fun updateRegisteredEvent(registeredEvent: RegisteredEvent): Flow<Boolean>
    = MockDb.updateRegisteredEvent(registeredEvent)

    override fun deleteRegisteredEvent(id: Long): Flow<Boolean>
            = MockDb.deleteRegisteredEvent(id)

}

