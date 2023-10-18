package br.com.rafaelleal.minhasferias.domain.models

data class RegisteredEvent(
    var name: String,
    var address: String,
    var time: String,
    var day: String,
    var id: Long
)
