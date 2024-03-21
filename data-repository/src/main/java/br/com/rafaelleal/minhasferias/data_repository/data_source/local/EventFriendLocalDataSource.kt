package br.com.rafaelleal.minhasferias.data_repository.data_source.local

import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow

interface EventFriendLocalDataSource {
    fun getFriendsFromEvent(eventId: Long): Flow<List<Friend>>
    fun changeEventFriendRelationship(eventId: Long, friendId: Long): Flow<Boolean>
}