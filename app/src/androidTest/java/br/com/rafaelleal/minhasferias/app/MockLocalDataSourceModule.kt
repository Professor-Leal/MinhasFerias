package br.com.rafaelleal.minhasferias.app

import br.com.rafaelleal.minhasferias.app.mocks.MockEventsFriendsLocalDataSource
import br.com.rafaelleal.minhasferias.app.mocks.MockFriendsLocalDataSource
import br.com.rafaelleal.minhasferias.data_local.injection.LocalDataSourceModule
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.app.mocks.MockRegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.EventFriendLocalDataSource
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.FriendsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [LocalDataSourceModule::class]
)
abstract class MockLocalDataSourceModule {
    @Binds
    abstract fun bindRegisteredEventsLocalDataSource(
        mockRegisteredEventsLocalDataSource: MockRegisteredEventsLocalDataSource
    ): RegisteredEventsLocalDataSource
    @Binds
    abstract fun bindFriendsLocalDataSource(
        mockFriendsLocalDataSource: MockFriendsLocalDataSource
    ): FriendsLocalDataSource
    @Binds
    abstract fun bindEventFriendLocalDataSource(
        mockEventsFriendsLocalDataSource: MockEventsFriendsLocalDataSource
    ): EventFriendLocalDataSource
}
