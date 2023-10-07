package br.com.rafaelleal.minhasferias.models

data class RegisteredEvent(
    val id: Long,
    val name: String,
    val address: String,
    val time: String,
    val day: String
)
