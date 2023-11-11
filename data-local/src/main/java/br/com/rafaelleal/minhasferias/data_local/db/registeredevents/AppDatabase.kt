package br.com.rafaelleal.minhasferias.data_local.db.registeredevents

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.rafaelleal.minhasferias.data_local.converters.RoomTypeConverters
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity



@Database(entities = [RegisteredEventEntity::class], version = ROOM_VERSION, exportSchema = true)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registeredEventDao(): RegisteredEventDao
}



val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE RegisteredEvent ADD COLUMN date INTEGER;")
    }
}

val MIGRATION_LIST = listOf(
    MIGRATION_1_2
).toTypedArray()

const val ROOM_VERSION = 2

