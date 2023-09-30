package br.com.rafaelleal.minhasferias.domain.repositories

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow

interface RegisteredEventsRepository {
    fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>>
}