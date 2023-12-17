package br.com.rafaelleal.minhasferias.data_local.db.registeredevents

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime

// Reference:
// https://blog.devgenius.io/testing-room-database-with-coroutines-and-flows-testing-fundamentals-iii-5f6c3b9e4c94

@RunWith(AndroidJUnit4::class)
class RegisteredEventDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var registeredEventDao: RegisteredEventDao

    val item01 = RegisteredEventEntity(
        name = "Evento 01",
        address = "Endereço 01",
        time = "12:00",
        day = "01/01/2023",
        date = LocalDateTime.of(2023,1,1,12,0)
    )
    val item02 = RegisteredEventEntity(
        name = "Evento 02",
        address = "Endereço 02",
        time = "12:30",
        day = "01/01/2023",
        date = LocalDateTime.of(2023,1,1,12,30)
    )

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        registeredEventDao = db.registeredEventDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    // CRUD Simples
    @Test
    fun save(): Unit = runBlocking {
        val allEmpty = registeredEventDao.getAll().first()
        assertEquals(allEmpty.size, 0)
        registeredEventDao.save(item01)
        val allInserted = registeredEventDao.getAll().first()
        assertEquals(allInserted.size, 1)
        assertEquals(allInserted[0].name, "Evento 01")
        assertEquals(allInserted[0].id, 1L)
    }

    @Test
    fun saveList(): Unit = runBlocking {
        val listInput = listOf(item01, item02)
        registeredEventDao.save(*listInput.toTypedArray())
        val list = registeredEventDao.getAll().first()
        assertThat(list.size).isEqualTo(2)
        assertThat(list[0].name).isEqualTo(item01.name)
        assertThat(list[0].id).isEqualTo(1L)
        assertThat(list[1].name).isEqualTo(item02.name)
        assertThat(list[1].id).isEqualTo(2L)
    }

    @Test
    fun getRegisteredEvent(): Unit = runBlocking {
        val listInput = listOf(item01)
        registeredEventDao.save(*listInput.toTypedArray())
        val requestedItem = registeredEventDao.getRegisteredEvent(1L).first()
        assertThat(requestedItem.name).isEqualTo(item01.name)
        assertThat(requestedItem.id).isEqualTo(1L)
    }

    @Test
    fun updateRegisteredEvent(): Unit = runBlocking {
        val listInput = listOf(item01)
        registeredEventDao.save(*listInput.toTypedArray())
        val requestedItem = registeredEventDao.getRegisteredEvent(1L).first()
        assertThat(requestedItem.name).isEqualTo(item01.name)
        assertThat(requestedItem.id).isEqualTo(1L)

        val updatedItem = requestedItem.copy(name = "Updated")
        val updatedItemsNumber = registeredEventDao.updateRegisteredEvent(updatedItem)
        assertThat(updatedItemsNumber).isEqualTo(1)

        val updatedRequestedItem = registeredEventDao.getRegisteredEvent(1L).first()
        assertThat(updatedRequestedItem.name).isEqualTo("Updated")
        assertThat(updatedRequestedItem.id).isEqualTo(1L)
    }

    @Test
    fun deleteRegisteredEvent(): Unit = runBlocking {
        val allEmpty = registeredEventDao.getAll().first()
        assertEquals(allEmpty.size, 0)
        registeredEventDao.save(item01)
        val allInserted = registeredEventDao.getAll().first()
        assertEquals(allInserted.size, 1)
        assertEquals(allInserted[0].name, "Evento 01")
        assertEquals(allInserted[0].id, 1L)

        val deletedItemsNumber = registeredEventDao.deleteRegisteredEvent(1L)
        assertThat(deletedItemsNumber).isEqualTo(1)

        val allEmptyAfterDelete = registeredEventDao.getAll().first()
        assertEquals(allEmptyAfterDelete.size, 0)

    }

}
