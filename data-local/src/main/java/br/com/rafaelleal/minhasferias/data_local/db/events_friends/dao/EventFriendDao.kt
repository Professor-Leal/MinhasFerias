package br.com.rafaelleal.minhasferias.data_local.db.events_friends.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities.EventFriendRelationshipEntity
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventFriendDao {
    @Query("SELECT Friend.* FROM Friend INNER JOIN EventFriend ON Friend.id = EventFriend.friendId WHERE EventFriend.registeredEventId = :eventId")
    fun getFriendsFromEvent(eventId: Long): Flow<List<FriendEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg eventFriendRelationshipEntity: EventFriendRelationshipEntity)

    @Query("SELECT * FROM EventFriend WHERE registeredEventId = :eventId AND friendId = :friendId")
    fun getByEventIdAndFriendId(eventId: Long, friendId: Long): EventFriendRelationshipEntity?

    @Query("DELETE FROM EventFriend WHERE id = :id")
    fun delete(id: Long)

}