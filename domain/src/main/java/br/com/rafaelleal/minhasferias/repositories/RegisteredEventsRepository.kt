package br.com.rafaelleal.minhasferias.repositories

import br.com.rafaelleal.minhasferias.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow

interface RegisteredEventsRepository {
    fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>>
}