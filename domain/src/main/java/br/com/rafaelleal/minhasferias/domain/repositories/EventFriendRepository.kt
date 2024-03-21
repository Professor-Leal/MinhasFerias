package br.com.rafaelleal.minhasferias.domain.repositories

import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow

interface EventFriendRepository {
    fun getFriendsFromEvent(eventId: Long): Flow<List<Friend>>
    fun changeEventFriendRelationship(eventId: Long, friendId: Long): Flow<Boolean>
}