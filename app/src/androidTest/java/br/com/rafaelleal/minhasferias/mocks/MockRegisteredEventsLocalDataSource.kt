package br.com.rafaelleal.minhasferias.mocks

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class MockRegisteredEventsLocalDataSource @Inject constructor() : RegisteredEventsLocalDataSource {


    override fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>> = flowOf(
        MockDb.registeredEventList.toList()

    )


}