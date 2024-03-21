package br.com.rafaelleal.minhasferias.data_local.db.events_friends.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.rafaelleal.minhasferias.data_local.db.friends.entities.FriendEntity
import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity

@Entity(
    tableName = "EventFriend",
    foreignKeys = [
        ForeignKey(
            entity = RegisteredEventEntity::class,
            parentColumns = ["id"],
            childColumns = ["registeredEventId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FriendEntity::class,
            parentColumns = ["id"],
            childColumns = ["friendId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventFriendRelationshipEntity(
    val registeredEventId: Long,
    val friendId: Long,
    @PrimaryKey(autoGenerate = true) var id: Long? = null
)
