package br.com.rafaelleal.minhasferias.data_local.converters

import br.com.rafaelleal.minhasferias.data_local.db.registerdevents.entities.RegisteredEventEntity
import br.com.rafaelleal.minhasferias.models.RegisteredEvent
import kotlin.reflect.full.memberProperties


fun RegisteredEventEntity.toModel() = with(::RegisteredEvent) {
    val propertiesByName = RegisteredEventEntity::class.memberProperties.associateBy { it.name }
    callBy(args = parameters.associate { parameter ->
        parameter to propertiesByName[parameter.name]?.get(this@toModel)
    })
}
fun RegisteredEvent.toEntity() = with(::RegisteredEventEntity) {
    val propertiesByName = RegisteredEvent::class.memberProperties.associateBy { it.name }
    callBy(args = parameters.associate { parameter ->
        parameter to propertiesByName[parameter.name]?.get(this@toEntity)
    })
}

