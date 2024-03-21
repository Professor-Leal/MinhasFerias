package br.com.rafaelleal.minhasferias.presentation_events_friends.models

data class EventWithFriendsUiModel (
    val eventId: Long,
    val eventName: String,
    val eventTime: String,
    val friendList: List<EventFriendListItem> = emptyList()
)