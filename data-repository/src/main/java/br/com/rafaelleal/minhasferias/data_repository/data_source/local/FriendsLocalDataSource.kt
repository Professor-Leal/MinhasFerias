package br.com.rafaelleal.minhasferias.data_repository.data_source.local

import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow

interface FriendsLocalDataSource {
    fun getAllFriends(): Flow<List<Friend>>
    fun saveFriend(friend: Friend)
    fun getFriend(id: Long): Flow<Friend>
    fun updateFriend(friend: Friend): Flow<Boolean>
    fun deleteFriend(id: Long): Flow<Boolean>
}