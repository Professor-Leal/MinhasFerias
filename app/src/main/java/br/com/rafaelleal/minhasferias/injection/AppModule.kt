package br.com.rafaelleal.minhasferias.injection

import br.com.rafaelleal.minhasferias.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideUseCaseConfiguration() = UseCase.Configuration(Dispatchers.IO)
}