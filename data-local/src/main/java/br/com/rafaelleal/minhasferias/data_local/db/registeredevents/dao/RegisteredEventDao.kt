package br.com.rafaelleal.minhasferias.data_local.db.registeredevents.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisteredEventDao {
    @Query("SELECT * FROM RegisteredEvent")
    fun getAll(): Flow<List<RegisteredEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg registeredEvent: RegisteredEventEntity)

}
