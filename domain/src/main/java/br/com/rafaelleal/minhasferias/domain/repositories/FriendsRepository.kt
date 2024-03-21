package br.com.rafaelleal.minhasferias.domain.repositories

import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    fun getAllFriends(): Flow<List<Friend>>
    fun saveFriend(registeredEvent: Friend)
    fun getFriend(id: Long): Flow<Friend>
    fun updateFriend(registeredEvent: Friend): Flow<Boolean>
    fun deleteFriend(id: Long): Flow<Boolean>
    fun searchFriendsByName(searchInput: String):  Flow<List<Friend>>
}