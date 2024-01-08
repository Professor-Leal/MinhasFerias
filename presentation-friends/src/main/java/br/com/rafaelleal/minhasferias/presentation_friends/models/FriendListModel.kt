package br.com.rafaelleal.minhasferias.presentation_friends.models

data class FriendListModel (
    val headerText: String = "",
    val items: List<FriendListItemModel> = listOf(),
)