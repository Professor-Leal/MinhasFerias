package br.com.rafaelleal.minhasferias.data_local.injection

import br.com.rafaelleal.minhasferias.data_local.source.RegisteredEventsLocalDataSourceImpl
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.RegisteredEventsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRegisteredEventsDataSource(
        localRegisteredEventsDataSourceImpl: RegisteredEventsLocalDataSourceImpl
    ): RegisteredEventsLocalDataSource

}