package br.com.rafaelleal.minhasferias.data_local.source

import br.com.rafaelleal.minhasferias.data_local.converters.toEntity
import br.com.rafaelleal.minhasferias.data_local.converters.toModel
import br.com.rafaelleal.minhasferias.data_local.db.friends.dao.FriendDao
import br.com.rafaelleal.minhasferias.data_repository.data_source.local.FriendsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FriendsLocalDataSourceImpl  @Inject constructor(
    private val friendDao: FriendDao
) : FriendsLocalDataSource {
    override fun getAllFriends(): Flow<List<Friend>> =
        friendDao.getAll().map { _friends ->
            _friends.map { _friend -> _friend.toModel() }
        }

    override fun saveFriend(friend: Friend) =
        friendDao.save(friend.toEntity().copy(id = null))

    override fun getFriend(id: Long): Flow<Friend> =
        friendDao.getFriend(id).map { _friend ->
            _friend.toModel()
        }

    override fun updateFriend(friend: Friend): Flow<Boolean> = flow {
        val updatedItems = friendDao.updateFriend(friend.toEntity())
        when (updatedItems) {
            1 -> emit(true)
            else -> emit(false)
        }
    }

    override fun deleteFriend(id: Long): Flow<Boolean> = flow {
        val deletedItems = friendDao.deleteFriend(id)
        when (deletedItems) {
            1 -> emit(true)
            else -> emit(false)
        }
    }

}


