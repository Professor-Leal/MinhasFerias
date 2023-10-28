package br.com.rafaelleal.minhasferias.mocks

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class MockRegisteredEventsLocalDataSource @Inject constructor() : RegisteredEventsLocalDataSource {
    override fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>> = flowOf(
        listOf<RegisteredEvent>(
            RegisteredEvent(
                name = "name 1",
                address = "address 1",
                time = "time",
                day = "day",
                id = 1L
            ),
            RegisteredEvent(
                name = "name 2",
                address = "address 2",
                time = "time 2 ",
                day = "day 2 ",
                id = 2L
            )
        )
    )
}