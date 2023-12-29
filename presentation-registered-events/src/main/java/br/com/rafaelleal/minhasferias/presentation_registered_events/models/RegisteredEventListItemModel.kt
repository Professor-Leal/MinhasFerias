package br.com.rafaelleal.minhasferias.presentation_registered_events.models

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent

data class RegisteredEventListItemModel(
    var name: String,
    var address: String,
    var dayTime: String,
    var id: Long
)

fun RegisteredEvent.toListItemModel(): RegisteredEventListItemModel{
    return  RegisteredEventListItemModel(
        name=this.name, address=this.address, dayTime="${this.day} - ${this.time}", id=this.id
    )
}
