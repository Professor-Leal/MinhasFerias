package br.com.rafaelleal.minhasferias.data_local.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.rafaelleal.minhasferias.data_local.converters.RoomTypeConverters
import br.com.rafaelleal.minhasferias.data_local.db.friends.dao.FriendDao
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity


@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [
        RegisteredEventEntity::class,
        FriendEntity::class
    ],
    version = ROOM_VERSION, exportSchema = true
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registeredEventDao(): RegisteredEventDao
    abstract fun friendDao(): FriendDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE RegisteredEvent ADD COLUMN date INTEGER;")
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Friend` (`name` TEXT NOT NULL, `surname` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `documentNumber` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)")
    }
}


val MIGRATION_LIST = listOf(
    MIGRATION_1_2, MIGRATION_2_3
).toTypedArray()

fun getMigrationListUntilVersion(position: Int) : Array<Migration> {
    if (position < MIGRATION_LIST.size - 1) {
        val sublist = MIGRATION_LIST.slice(0..(position - 2)).toTypedArray()
    }
    return MIGRATION_LIST
}

const val ROOM_VERSION = 3

