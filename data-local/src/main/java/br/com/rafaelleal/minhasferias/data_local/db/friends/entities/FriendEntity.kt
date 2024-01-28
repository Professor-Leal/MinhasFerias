package br.com.rafaelleal.minhasferias.data_local.db.friends.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Friend")
data class FriendEntity(
    var name: String = "",
    var surname: String = "",
    var phoneNumber: String = "",
    var documentNumber: String = "",
    @PrimaryKey(autoGenerate = true) var id: Long? = null
)