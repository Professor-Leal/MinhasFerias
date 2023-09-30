package br.com.rafaelleal.domain.repositories

import br.com.rafaelleal.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow

interface RegisteredEventsRepository {
    fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>>
}