package br.com.rafaelleal.minhasferias.data_local.injection

import android.content.Context
import androidx.room.Room
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.AppDatabase
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


internal const val DATABASE_NAME = "MINHAS_FERIAS_DATABASE"

@Module
@InstallIn(ViewModelComponent::class)
class PersistenceModule {

    @Provides
    @ViewModelScoped
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).build()

    @Provides
    @ViewModelScoped
    fun provideRegisteredEventDao(appDatabase: AppDatabase): RegisteredEventDao =
        appDatabase.registeredEventDao()
}