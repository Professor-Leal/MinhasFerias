package br.com.rafaelleal.minhasferias.app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.SyncStateContract.Helpers.insert
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rafaelleal.minhasferias.data_local.db.AppDatabase
import br.com.rafaelleal.minhasferias.data_local.db.MIGRATION_1_2
import br.com.rafaelleal.minhasferias.data_local.db.MIGRATION_2_3
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.data_local.db.getMigrationListUntilVersion
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.ZoneOffset

@RunWith(AndroidJUnit4::class)
class RoomMigrationTest {

    private val TEST_DB = "test-db"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName
    )

    @Test
    @Throws(IOException::class)
    fun migrate_0001_To_0002() {
        val previousVersionRegisteredEvent = buildRegisteredEventToInsert_V001()
        helper.createDatabase(TEST_DB, 1).apply {
            insert(
                "RegisteredEvent",
                SQLiteDatabase.CONFLICT_REPLACE,
                previousVersionRegisteredEvent
            )
            close()
        }
        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        val migratedDatabase_1_2 = getMigratedRoomDatabase_1_2()
        val migratedRegisteredEvent = migratedDatabase_1_2.registeredEventDao().getById(1)
        assertEquals(null, migratedRegisteredEvent.date)
    }

    @Test
    @Throws(IOException::class)
    fun migrate_0002_To_0003() {
        helper.createDatabase(TEST_DB, 2)
        val friendToInsert = buildFriendToInsert_V003()
        helper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_2_3).apply {
            insert(
                "Friend",
                SQLiteDatabase.CONFLICT_REPLACE,
                friendToInsert
            )
            close()
        }
        val migratedDatabase_2_3 = getMigratedRoomDatabase_2_3()
        val friend = migratedDatabase_2_3.friendDao().getById(1L)
        assertEquals("João", friend.name )
    }

    private fun buildRegisteredEventToInsert_V001(): ContentValues {
        val registeredEvent = ContentValues().apply {
            put("id", "1")
            put("name", "name")
            put("address", "address")
            put("time", "time")
            put("day", "day")
        }
        return registeredEvent
    }

    private fun buildRegisteredEventToInsert_V002(): ContentValues {
        val registeredEvent = ContentValues().apply {
            put("id", "1")
            put("name", "name")
            put("address", "address")
            put("time", "time")
            put("day", "day")
            put("date", "0")
        }
        return registeredEvent
    }

    private fun buildFriendToInsert_V003(): ContentValues {
        val friend = ContentValues().apply {
            put("id", "1")
            put("name", "João")
            put("surname", "Silva")
            put("phoneNumber", "99999-9999")
            put("documentNumber", "111.222.333-00")
        }
        return friend
    }

    private fun getMigratedRoomDatabase_1_2(): AppDatabase {
        return Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            TEST_DB
        ).addMigrations(
            *getMigrationListUntilVersion(2)
        ).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

    private fun getMigratedRoomDatabase_2_3(): AppDatabase {
        return Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            TEST_DB
        ).addMigrations(
            *getMigrationListUntilVersion(3)
        ).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

}



