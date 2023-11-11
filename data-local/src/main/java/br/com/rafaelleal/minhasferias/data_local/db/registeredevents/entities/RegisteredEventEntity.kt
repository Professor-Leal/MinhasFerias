package br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "RegisteredEvent")
data class RegisteredEventEntity(
    var name: String = "",
    var address: String = "",
    var time: String = "",
    var day: String = "",
    var date: LocalDateTime? = null,
    @PrimaryKey(autoGenerate = true) var id: Long? = null
)

