package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.converters.toModel
import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisteredEventsLocalDataSourceImpl @Inject constructor(
    private val registeredEventDao: RegisteredEventDao
) : RegisteredEventsLocalDataSource {
    override fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>> =
        registeredEventDao.getAll().map { _events ->
            _events.map { _event -> _event.toModel() }
        }
}


