package br.com.rafaelleal.minhasferias.data_local.injection

import android.content.Context
import androidx.room.Room
import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.AppDatabase
import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.dao.RegisteredEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


internal const val DATABASE_NAME = "MINHAS_FERIAS_DATABASE"
@Module
@InstallIn(ViewModelComponent::class)
class PersistenceModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "DATABASE_NAME"
        ).build()

    @Provides
    fun provideRegisteredEventDao(appDatabase: AppDatabase): RegisteredEventDao =
        appDatabase.registeredEventDao()
}