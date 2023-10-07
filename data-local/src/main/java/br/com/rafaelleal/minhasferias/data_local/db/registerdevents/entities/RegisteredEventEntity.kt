package br.com.rafaelleal.minhasferias.data_local.db.registerdevents.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RegisteredEvent")
data class RegisteredEventEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    val name: String,
    val address: String,
    val time: String,
    val day: String
)

