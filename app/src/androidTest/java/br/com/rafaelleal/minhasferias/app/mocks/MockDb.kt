package br.com.rafaelleal.minhasferias.app.mocks

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.sealed.UseCaseException
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class MockDb {
    companion object {

        // Friends
        var friendId = 1L
        var friendList = mutableListOf<Friend>()
        private val _friendsListFlow = MutableStateFlow<List<Friend>>(listOf())

        fun getAllFriends(): StateFlow<List<Friend>> = _friendsListFlow

        fun addFriend(friend: Friend) {
            friendList.add(friend.copy(id = friendId++))
            _friendsListFlow.value = friendList
        }

        fun getFriend(id: Long): Flow<Friend> = flow {
            _friendsListFlow.value.filter {
                it.id == id
            }.firstOrNull()?.let { emit(it) } ?: throw UseCaseException.FriendException(
                Throwable("Friend of id = $id not found")
            )
        }

        fun updateFriend(friend: Friend): Flow<Boolean> = flow {
            val actualList = _friendsListFlow.value.toMutableList()
            val filteredList = actualList.filter { it.id == friend.id }
            if (filteredList.isNullOrEmpty()) {
                emit(false)
            } else {
                actualList.remove(filteredList.get(0))
                actualList.add(friend)
                _friendsListFlow.value = actualList.sortedBy { it.id }
                emit(true)
            }
        }

        fun deleteFriend(id: Long): Flow<Boolean> = flow {
            val actualList = _friendsListFlow.value.toMutableList()
            val filteredList = actualList.filter { it.id == id }
            if (filteredList.isNullOrEmpty()) {
                emit(false)
            } else {
                actualList.remove(filteredList.get(0))
                _friendsListFlow.value = actualList.sortedBy { it.id }
                emit(true)
            }
        }

        fun searchFriendsByName(searchInput: String): Flow<List<Friend>> = flow {
            val filteredList: MutableList<Friend> = _friendsListFlow.value.filter {
                it.name.contains(searchInput) || it.surname.contains(searchInput)
            }.toMutableList()
            _friendsListFlow.value = filteredList.toList()
            emit(filteredList)
        }

        // RegisteredEvents
        var registeredEventId = 1L
        var registeredEventList = mutableListOf<RegisteredEvent>()
        private val _registeredEventsListFlow = MutableStateFlow<List<RegisteredEvent>>(listOf())

        fun getAllRegisteredEvents(): StateFlow<List<RegisteredEvent>> = _registeredEventsListFlow

        fun addRegisteredEvent(registeredEvent: RegisteredEvent) {
            registeredEventList.add(registeredEvent.copy(id = registeredEventId++))
            _registeredEventsListFlow.value = registeredEventList
        }

        fun addTwoRegisteredEventsToDB() {
            registeredEventList = mutableListOf<RegisteredEvent>(
                RegisteredEvent(
                    name = "name 1",
                    address = "address 1",
                    time = "12:05",
                    day = "01/02/2023",
                    id = 1L
                ),
                RegisteredEvent(
                    name = "name 2",
                    address = "address 2",
                    time = "12:37",
                    day = "01/02/2023 ",
                    id = 2L
                )
            )
            _registeredEventsListFlow.value = registeredEventList
        }

        fun addTwoFriendsToDB() {
            friendList = mutableListOf<Friend>(
                Friend(
                    name = "John",
                    surname = "Doe",
                    phoneNumber = "555-0001",
                    documentNumber = "111.111.111-11",
                    id = 1L
                ),
                Friend(
                    name = "Jane",
                    surname = "Doe",
                    phoneNumber = "555-0002",
                    documentNumber = "111.111.111-22",
                    id = 2L
                )
            )
            _friendsListFlow.value = friendList
        }

        fun getRegisteredEvent(id: Long): Flow<RegisteredEvent> = flow {
            _registeredEventsListFlow.value.filter {
                it.id == id
            }.firstOrNull()?.let { emit(it) } ?: throw UseCaseException.RegisteredEventException(
                Throwable("RegisteredEvent of id = $id not found")
            )
        }

        fun updateRegisteredEvent(registeredEvent: RegisteredEvent): Flow<Boolean> = flow {
            val actualList = _registeredEventsListFlow.value.toMutableList()
            val filteredList = actualList.filter { it.id == registeredEvent.id }
            if (filteredList.isNullOrEmpty()) {
                emit(false)
            } else {
                actualList.remove(filteredList.get(0))
                actualList.add(registeredEvent)
                _registeredEventsListFlow.value = actualList.sortedBy { it.id }
                emit(true)
            }
        }

        fun deleteRegisteredEvent(id: Long): Flow<Boolean> = flow {
            val actualList = _registeredEventsListFlow.value.toMutableList()
            val filteredList = actualList.filter { it.id == id }
            if (filteredList.isNullOrEmpty()) {
                emit(false)
            } else {
                actualList.remove(filteredList.get(0))
                _registeredEventsListFlow.value = actualList.sortedBy { it.id }
                emit(true)
            }
        }

        fun clearAll() {
            registeredEventId = 1L
            registeredEventList = mutableListOf<RegisteredEvent>()

            friendId = 1L
            friendList = mutableListOf<Friend>()
        }
    }
}