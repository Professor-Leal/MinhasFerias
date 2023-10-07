package br.com.rafaelleal.minhasferias.data_repository.injection

import br.com.rafaelleal.minhasferias.data_repository.repositories.RegisteredEventsRepositoryImpl
import br.com.rafaelleal.minhasferias.repositories.RegisteredEventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRegisteredEventsRepository(
        registeredEventsRepositoryImpl: RegisteredEventsRepositoryImpl
    ): RegisteredEventsRepository
}

