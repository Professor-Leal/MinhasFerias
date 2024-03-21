package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.converters.toModel
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.dao.EventFriendDao
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities.EventFriendRelationshipEntity
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.EventFriendLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventFriendLocalDataSourceImpl @Inject constructor(
    private val eventFriendDao: EventFriendDao
) : EventFriendLocalDataSource {
    override fun getFriendsFromEvent(eventId: Long): Flow<List<Friend>> =
        eventFriendDao.getFriendsFromEvent(eventId).map { _friendList ->
            _friendList.map { it.toModel() }
        }

    override fun changeEventFriendRelationship(eventId: Long, friendId: Long): Flow<Boolean> = flow {
        val relation = eventFriendDao.getByEventIdAndFriendId(eventId, friendId)
        relation?.let{ _relation ->
            eventFriendDao.delete(_relation.id ?: 0)
            emit(true)
        } ?: run {
            eventFriendDao.save( EventFriendRelationshipEntity(eventId, friendId) )
            emit(true)
        }
    }
}