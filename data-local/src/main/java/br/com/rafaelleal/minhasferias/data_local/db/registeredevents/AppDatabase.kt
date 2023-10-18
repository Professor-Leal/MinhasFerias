package br.com.rafaelleal.minhasferias.data_local.db.registeredevents

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity


@Database(entities = [RegisteredEventEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registeredEventDao(): RegisteredEventDao
}