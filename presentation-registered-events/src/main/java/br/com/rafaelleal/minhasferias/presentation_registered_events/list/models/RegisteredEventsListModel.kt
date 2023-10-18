package br.com.rafaelleal.minhasferias.presentation_registered_events.list.models

data class RegisteredEventsListModel(
    val headerText: String = "",
    val items: List<RegisteredEventListItemModel> = listOf(),
)
