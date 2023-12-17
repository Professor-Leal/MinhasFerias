package br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisteredEventDao {
    @Query("SELECT * FROM RegisteredEvent")
    fun getAll(): Flow<List<RegisteredEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg registeredEvent: RegisteredEventEntity)

    @Query("SELECT * FROM RegisteredEvent WHERE id = :id")
    fun getById(id: Long): RegisteredEventEntity

    @Query("SELECT * FROM RegisteredEvent WHERE id = :id")
    fun getRegisteredEvent(id: Long): Flow<RegisteredEventEntity>

    @Update
    fun updateRegisteredEvent(registeredEvent: RegisteredEventEntity): Int

    @Query("DELETE FROM RegisteredEvent WHERE id = :id")
    fun deleteRegisteredEvent(id: Long): Int

}

