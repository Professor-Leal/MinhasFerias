package br.com.rafaelleal.minhasferias.app

import br.com.rafaelleal.minhasferias.data_local.injection.LocalDataSourceModule
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import br.com.rafaelleal.minhasferias.app.mocks.MockRegisteredEventsLocalDataSource
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
}
