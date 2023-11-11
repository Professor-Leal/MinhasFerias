package br.com.rafaelleal.minhasferias.app.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.AppDatabase
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.MIGRATION_1_2
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

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
            insert("RegisteredEvent", SQLiteDatabase.CONFLICT_REPLACE, previousVersionRegisteredEvent)
            close()
        }
        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        val migratedDatabase_1_2 = getMigratedRoomDatabase_1_2()
        val migratedRegisteredEvent = migratedDatabase_1_2.registeredEventDao().getById(1)
        assertEquals(null, migratedRegisteredEvent.date)
    }

    private fun buildRegisteredEventToInsert_V001(): ContentValues {
        val registeredEvent = ContentValues().apply{
            put("id", "1")
            put("name", "name")
            put("address", "address")
            put("time", "time")
            put("day", "day")
        }
        return registeredEvent
    }
    private fun buildRegisteredEventToInsert_V002(): ContentValues {
        val registeredEvent = ContentValues().apply{
            put("id", "1")
            put("name", "name")
            put("address", "address")
            put("time", "time")
            put("day", "day")
            put("date", "0")
        }
        return registeredEvent
    }

    private fun getMigratedRoomDatabase_1_2(): AppDatabase {
        return Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            TEST_DB
        ).addMigrations(
            MIGRATION_1_2
        ).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

}



