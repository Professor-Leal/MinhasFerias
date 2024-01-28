package br.com.rafaelleal.minhasferias.data_repository.injection

import br.com.rafaelleal.minhasferias.data_repository.repositories.FriendsRepositoryImpl
import br.com.rafaelleal.minhasferias.data_repository.repositories.RegisteredEventsRepositoryImpl
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import br.com.rafaelleal.minhasferias.domain.repositories.RegisteredEventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRegisteredEventsRepository(
        registeredEventsRepositoryImpl: RegisteredEventsRepositoryImpl
    ): RegisteredEventsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindFriendsRepository(
        friendsRepositoryImpl: FriendsRepositoryImpl
    ): FriendsRepository
}