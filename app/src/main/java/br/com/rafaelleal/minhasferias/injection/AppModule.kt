package br.com.rafaelleal.minhasferias.injection

import br.com.rafaelleal.minhasferias.domain.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideUseCaseConfiguration() = UseCase.Configuration(Dispatchers.IO)
}