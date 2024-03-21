package br.com.rafaelleal.minhasferias.app.mocks

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rafaelleal.minhasferias.data_local.converters.toEntity
import br.com.rafaelleal.minhasferias.data_local.converters.toModel
import br.com.rafaelleal.minhasferias.data_local.db.AppDatabase
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.dao.EventFriendDao
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities.EventFriendRelationshipEntity
import br.com.rafaelleal.minhasferias.data_local.db.friends.dao.FriendDao
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao.RegisteredEventDao
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class MockDb {
    companion object {

        var database: AppDatabase? = null
        var registeredEventDao: RegisteredEventDao? = null
        var friendDao: FriendDao? = null
        var eventFriendDao: EventFriendDao? = null

        fun initializeDatabase() {
            if (database == null) {
                val context = InstrumentationRegistry.getInstrumentation().targetContext
                database = Room.inMemoryDatabaseBuilder(
                    context, AppDatabase::class.java
                ).allowMainThreadQueries().build()
                eventFriendDao = database?.eventFriendDao()
                registeredEventDao = database?.registeredEventDao()
                friendDao = database?.friendDao()
            }
        }

        fun closeDatabase() {
            database?.close()
            database = null
            eventFriendDao = null
            registeredEventDao = null
            friendDao = null
        }

        fun getFriendsFromEvent(eventId: Long): Flow<List<Friend>> =
            database!!.eventFriendDao().getFriendsFromEvent(eventId)
                .map { _list ->
                    _list.map {
                        it.toModel()
                    }
                }

        fun changeEventFriendRelationship(eventId: Long, friendId: Long): Flow<Boolean> = flow {
            val relation = database!!.eventFriendDao().getByEventIdAndFriendId(eventId, friendId)
            relation?.let { _relation ->
                database!!.eventFriendDao().delete(_relation.id ?: 0)
                emit(true)
            } ?: run {
                database!!.eventFriendDao().save(EventFriendRelationshipEntity(eventId, friendId))
                emit(true)
            }
        }

//

        fun getAllFriends(): Flow<List<Friend>> = database!!.friendDao().getAll().map { _friends ->
            _friends.map { _friend -> _friend.toModel() }
        }

        fun addFriend(friend: Friend) =
            database!!.friendDao().save(friend.toEntity().copy(id = null))


        fun getFriend(id: Long): Flow<Friend> =
            database!!.friendDao().getFriend(id).map { _friend ->
                _friend.toModel()
            }


        fun updateFriend(friend: Friend): Flow<Boolean> = flow {
            val updatedItems = database!!.friendDao().updateFriend(friend.toEntity())
            when (updatedItems) {
                1 -> emit(true)
                else -> emit(false)
            }
        }

        fun deleteFriend(id: Long): Flow<Boolean> = flow {
            val deletedItems = database!!.friendDao().deleteFriend(id)
            when (deletedItems) {
                1 -> emit(true)
                else -> emit(false)
            }
        }

        fun searchFriendsByName(searchInput: String): Flow<List<Friend>> =
            database!!.friendDao().searchFriendsByName(searchInput).map { _friends ->
                _friends.map { _friend -> _friend.toModel() }
            }


        fun getAllRegisteredEvents(): Flow<List<RegisteredEvent>> =
            database!!.registeredEventDao().getAll().map { _list ->
                _list.map {
                    it.toModel()
                }
            }

        fun addRegisteredEvent(registeredEvent: RegisteredEvent) =
            database!!.registeredEventDao().save(registeredEvent.toEntity().copy(id = null))

        fun getRegisteredEvent(id: Long): Flow<RegisteredEvent> =
            database!!.registeredEventDao().getRegisteredEvent(id).map { _event ->
                _event.toModel()
            }

        fun updateRegisteredEvent(registeredEvent: RegisteredEvent): Flow<Boolean> = flow {
            val updatedItems =
                database!!.registeredEventDao().updateRegisteredEvent(registeredEvent.toEntity())
            when (updatedItems) {
                1 -> emit(true)
                else -> emit(false)
            }
        }

        fun deleteRegisteredEvent(id: Long): Flow<Boolean> = flow {
            val deletedItems = database!!.registeredEventDao().deleteRegisteredEvent(id)
            when (deletedItems) {
                1 -> emit(true)
                else -> emit(false)
            }
        }


        fun addEventFriendToDb() {
            database!!.eventFriendDao().save(
                EventFriendRelationshipEntity(
                    id = 1L,
                    registeredEventId = 1L,
                    friendId = 1L
                )
            )
        }

        fun addTwoFriendsToDB() {
            database!!.friendDao().save(
                *listOf(
                    FriendEntity(
                        name = "John",
                        surname = "Doe",
                        phoneNumber = "555-0001",
                        documentNumber = "111.111.111-11",
                        id = 1L
                    ),
                    FriendEntity(
                        name = "Jane",
                        surname = "Doe",
                        phoneNumber = "555-0002",
                        documentNumber = "111.111.111-22",
                        id = 2L
                    )
                ).toTypedArray()
            )
        }

        fun addTwoRegisteredEventsToDB() {
            database?.registeredEventDao()?.save(
                RegisteredEventEntity(
                    name = "name 1",
                    address = "address 1",
                    time = "12:05",
                    day = "01/02/2023",
                    id = 1L,
                    date = LocalDateTime.of(2023, 2, 1, 12, 5)
                ),
            )

            database?.registeredEventDao()?.save(
                RegisteredEventEntity(
                    name = "name 2",
                    address = "address 2",
                    time = "12:37",
                    day = "01/02/2023 ",
                    id = 2L,
                    date = LocalDateTime.of(2023, 2, 1, 12, 37)
                ),
            )

        }


    }
}