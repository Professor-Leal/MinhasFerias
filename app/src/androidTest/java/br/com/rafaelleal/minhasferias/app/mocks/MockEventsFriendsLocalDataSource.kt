package br.com.rafaelleal.minhasferias.app.mocks

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.EventFriendLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MockEventsFriendsLocalDataSource @Inject constructor() : EventFriendLocalDataSource {
    override fun getFriendsFromEvent(eventId: Long): Flow<List<Friend>> =
        MockDb.getFriendsFromEvent(eventId)

    override fun changeEventFriendRelationship(eventId: Long, friendId: Long): Flow<Boolean> =
        MockDb.changeEventFriendRelationship(eventId, friendId)

}