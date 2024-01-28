package br.com.rafaelleal.minhasferias.data_repository.repositories

import br.com.rafaelleal.minhasferias.data_repository.data_source.local.FriendsLocalDataSource
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.repositories.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FriendsRepositoryImpl  @Inject constructor(
    private val friendsLocalDataSource: FriendsLocalDataSource
) : FriendsRepository {
    override fun getAllFriends(): Flow<List<Friend>>
            = friendsLocalDataSource.getAllFriends()

    override fun saveFriend(friend: Friend)
            = friendsLocalDataSource.saveFriend(friend)

    override fun getFriend(id: Long): Flow<Friend>
            = friendsLocalDataSource.getFriend(id)

    override fun updateFriend(friend: Friend): Flow<Boolean>
            = friendsLocalDataSource.updateFriend(friend)

    override fun deleteFriend(id: Long): Flow<Boolean>
            = friendsLocalDataSource.deleteFriend(id)

}
