package br.com.rafaelleal.minhasferias.data_local.db.friends.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FriendDao {
    @Query("SELECT * FROM Friend")
    fun getAll(): Flow<List<FriendEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg friend: FriendEntity)

    @Query("SELECT * FROM Friend WHERE id = :id")
    fun getById(id: Long): FriendEntity

    @Query("SELECT * FROM Friend WHERE id = :id")
    fun getFriend(id: Long): Flow<FriendEntity>

    @Update
    fun updateFriend(friend: FriendEntity): Int

    @Query("DELETE FROM Friend WHERE id = :id")
    fun deleteFriend(id: Long): Int

}

