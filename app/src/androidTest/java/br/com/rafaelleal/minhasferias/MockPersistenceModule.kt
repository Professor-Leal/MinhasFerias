package br.com.rafaelleal.minhasferias

import android.content.Context
import androidx.room.Room
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.AppDatabase
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.injection.PersistenceModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.testing.TestInstallIn


//@Module
//@TestInstallIn(
//    components = [ViewModelComponent::class],
//    replaces = [PersistenceModule::class]
//)
class MockPersistenceModule {
//    @Provides
//    @ViewModelScoped
//    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
//        Room.inMemoryDatabaseBuilder(
//            context, AppDatabase::class.java
//        ).allowMainThreadQueries().build()
//
//    @Provides
//    @ViewModelScoped
//    fun provideRegisteredEventDao(appDatabase: AppDatabase): RegisteredEventDao =
//        appDatabase.registeredEventDao()
}