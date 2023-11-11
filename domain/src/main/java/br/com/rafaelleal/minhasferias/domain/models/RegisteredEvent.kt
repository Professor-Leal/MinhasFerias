package br.com.rafaelleal.minhasferias.domain.models

import java.time.LocalDateTime
import java.util.Date

data class RegisteredEvent(
    var name: String,
    var address: String,
    var time: String,
    var day: String,
    var date: LocalDateTime? = null,
    var id: Long
)
