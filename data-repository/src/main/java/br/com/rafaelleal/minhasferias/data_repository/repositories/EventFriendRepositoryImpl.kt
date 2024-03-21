package br.com.rafaelleal.minhasferias.data_repository.repositories

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.EventFriendLocalDataSource
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.FriendsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.EventFriendRepository
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventFriendRepositoryImpl @Inject constructor(
    private val eventFriendLocalDataSource: EventFriendLocalDataSource
) : EventFriendRepository {
    override fun getFriendsFromEvent(eventId: Long): Flow<List<Friend>> =
        eventFriendLocalDataSource.getFriendsFromEvent(eventId)

    override fun changeEventFriendRelationship(eventId: Long, friendId: Long): Flow<Boolean> =
        eventFriendLocalDataSource.changeEventFriendRelationship(eventId, friendId)
}