package br.com.rafaelleal.minhasferias.domain.repositories

import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow

interface RegisteredEventsRepository {
    fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>>
    fun saveRegisteredEvent(registeredEvent: RegisteredEvent)
    fun getRegisteredEvent(id: Long): Flow<RegisteredEvent>
    fun updateRegisteredEvent(registeredEvent: RegisteredEvent): Flow<Boolean>
    fun deleteRegisteredEvent(id: Long): Flow<Boolean>
}