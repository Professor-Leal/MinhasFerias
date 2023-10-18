package br.com.rafaelleal.minhasferias.data_repository.repositories

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisteredEventsRepositoryImpl @Inject constructor(
    private val registeredEventsLocalDataSource: RegisteredEventsLocalDataSource
) : RegisteredEventsRepository {
    override fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>>
    = registeredEventsLocalDataSource.getAllRegisteredEvents()
}
