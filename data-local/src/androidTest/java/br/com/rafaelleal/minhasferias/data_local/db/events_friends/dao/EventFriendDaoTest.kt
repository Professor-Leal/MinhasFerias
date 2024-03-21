//package br.com.rafaelleal.minhasferias.data_local.db.events_friends.dao
//
//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import br.com.rafaelleal.minhasferias.data_local.db.AppDatabase
//import br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities.EventFriendRelationshipEntity
//import br.com.rafaelleal.minhasferias.data_local.db.friends.dao.FriendDao
//import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
//import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
//import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
//import com.google.common.truth.Truth.assertThat
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.runBlocking
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.io.IOException
//import java.time.LocalDateTime
//
//@RunWith(AndroidJUnit4::class)
//class EventFriendDaoTest {
//
//    private lateinit var db: AppDatabase
//    private lateinit var eventFriendDao: EventFriendDao
//    private lateinit var registeredEventDao: RegisteredEventDao
//    private lateinit var friendDao: FriendDao
//
//    val event01 = RegisteredEventEntity(
//        name = "Evento 01",
//        address = "Endereço 01",
//        time = "12:00",
//        day = "01/01/2023",
//        date = LocalDateTime.of(2023, 1, 1, 12, 0)
//    )
//
//    val friend01Mock = FriendEntity(
//        id = 1L,
//        name = "John",
//        surname = "Doe",
//        phoneNumber = "11111-1111",
//        documentNumber = "111.111.111-11"
//    )
//
//    val friend02Mock = FriendEntity(
//        id = 2L,
//        name = "Jane",
//        surname = "Doe",
//        phoneNumber = "11111-1112",
//        documentNumber = "111.111.111-12"
//    )
//
//    val friend03Mock = FriendEntity(
//        id = 3L,
//        name = "John",
//        surname = "Travolta",
//        phoneNumber = "11111-1113",
//        documentNumber = "111.111.111-13"
//    )
//    val friend04Mock = FriendEntity(
//        id = 4L,
//        name = "Deny",
//        surname = "Devito",
//        phoneNumber = "11111-1114",
//        documentNumber = "111.111.111-14"
//    )
//
//    val friend05Mock = FriendEntity(
//        id = 5L,
//        name = "Keanu",
//        surname = "Reeves",
//        phoneNumber = "11111-1115",
//        documentNumber = "111.111.111-15"
//    )
//
//    val listOfFriends = listOf(
//        friend01Mock,
//        friend02Mock,
//        friend03Mock,
//        friend04Mock,
//        friend05Mock
//    )
//
//    val relation01 = EventFriendRelationshipEntity(
//        registeredEventId = 1L,
//        friendId = 1L
//    )
//
//    val relation02 = EventFriendRelationshipEntity(
//        registeredEventId = 1L,
//        friendId = 2L
//    )
//    val relation03 = EventFriendRelationshipEntity(
//        registeredEventId = 1L,
//        friendId = 3L
//    )
//    val relation04 = EventFriendRelationshipEntity(
//        registeredEventId = 1L,
//        friendId = 4L
//    )
//    val relation05 = EventFriendRelationshipEntity(
//        registeredEventId = 1L,
//        friendId = 5L
//    )
//
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        db = Room.inMemoryDatabaseBuilder(
//            context, AppDatabase::class.java
//        ).allowMainThreadQueries().build()
//        eventFriendDao = db.eventFriendDao()
//        registeredEventDao = db.registeredEventDao()
//        friendDao = db.friendDao()
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    fun getFriendsFromEvent(): Unit = runBlocking {
//        registeredEventDao.save(event01)
//        friendDao.save(*listOfFriends.toTypedArray())
//        eventFriendDao.save(*listOf(relation04, relation03, relation05).toTypedArray())
//
//        val querylist = eventFriendDao.getFriendsFromEvent(1L).first()
//        assertThat(querylist.size).isEqualTo(3)
//        assertThat(
//            querylist.filter { it.name == "Deny" && it.surname == "Devito" }.size
//        ).isEqualTo(1)
//
//        assertThat(
//            querylist.filter { it.name == "Keanu" && it.surname == "Reeves" }.size
//        ).isEqualTo(1)
//
//        assertThat(
//            querylist.filter { it.name == "John" && it.surname == "Travolta" }.size
//        ).isEqualTo(1)
//
//        assertThat(
//            querylist.filter { it.name == "John" && it.surname == "Doe" }.size
//        ).isEqualTo(0)
//        assertThat(
//            querylist.filter { it.name == "Jane" && it.surname == "Doe" }.size
//        ).isEqualTo(0)
//
//    }
//
//
//}