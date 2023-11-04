package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.converters.toEntity
import br.com.rafaelleal.minhasferias.data_local.converters.toModel
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisteredEventsLocalDataSourceImpl @Inject constructor(
    private val registeredEventDao: RegisteredEventDao
) : RegisteredEventsLocalDataSource {
    override fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>> =
        registeredEventDao.getAll().map { _events ->
            _events.map { _event -> _event.toModel() }
        }

    override fun saveRegisteredEvent(registeredEvent: RegisteredEvent) =
        registeredEventDao.save(registeredEvent.toEntity().copy(id = null))
}


