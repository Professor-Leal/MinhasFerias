package br.com.rafaelleal.minhasferias.domain.adapters.date_time

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DateTimeModule {
    @Binds
    @ViewModelScoped
    abstract fun bindDateTimeAdapter(
        dateTimeAdapterImpl: DateTimeAdapterImpl
    ): DateTimeAdapter
}