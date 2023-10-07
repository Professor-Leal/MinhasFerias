package br.com.rafaelleal.minhasferias.data_local.db.registerdevents

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.entities.RegisteredEventEntity


@Database(entities = [RegisteredEventEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registeredEventDao(): RegisteredEventDao
}