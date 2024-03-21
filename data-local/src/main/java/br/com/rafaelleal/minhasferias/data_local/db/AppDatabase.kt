package br.com.rafaelleal.minhasferias.data_local.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.rafaelleal.minhasferias.data_local.converters.RoomTypeConverters
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.dao.EventFriendDao
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities.EventFriendRelationshipEntity
import br.com.rafaelleal.minhasferias.data_local.db.friends.dao.FriendDao
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity


@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [
        RegisteredEventEntity::class,
        FriendEntity::class,
        EventFriendRelationshipEntity::class
    ],
    version = ROOM_VERSION, exportSchema = true
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registeredEventDao(): RegisteredEventDao
    abstract fun friendDao(): FriendDao
    abstract fun eventFriendDao(): EventFriendDao
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
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL( "CREATE TABLE IF NOT EXISTS `EventFriend` (`registeredEventId` INTEGER NOT NULL, `friendId` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT, FOREIGN KEY(`registeredEventId`) REFERENCES `RegisteredEvent`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`friendId`) REFERENCES `Friend`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
    }
}




val MIGRATION_LIST = listOf(
    MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4
).toTypedArray()

fun getMigrationListUntilVersion(position: Int): Array<Migration> {
    if (position < MIGRATION_LIST.size - 1) {
        val sublist = MIGRATION_LIST.slice(0..(position - 2)).toTypedArray()
        return sublist
    }
    return MIGRATION_LIST
}

const val ROOM_VERSION = 4

