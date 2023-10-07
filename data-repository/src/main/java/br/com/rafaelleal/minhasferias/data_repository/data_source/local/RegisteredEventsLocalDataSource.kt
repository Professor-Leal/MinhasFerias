package br.com.rafaelleal.minhasferias.data_repository.data_source.local

import br.com.rafaelleal.minhasferias.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow

interface RegisteredEventsLocalDataSource {
    fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>>
}

