package br.com.rafaelleal.minhasferias.app.mocks

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.FriendsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MockFriendsLocalDataSource @Inject constructor() : FriendsLocalDataSource {
    override fun getAllFriends(): Flow<List<Friend>> =
        MockDb.getAllFriends()

    override fun saveFriend(friend: Friend) {
        MockDb.addFriend(friend)
    }

    override fun getFriend(id: Long): Flow<Friend> =
        MockDb.getFriend(id)

    override fun updateFriend(friend: Friend): Flow<Boolean> = MockDb.updateFriend(friend)

    override fun deleteFriend(id: Long): Flow<Boolean> = MockDb.deleteFriend(id)

    override fun searchFriendsByName(searchInput: String): Flow<List<Friend>> =
        MockDb.searchFriendsByName(searchInput)

}
