package br.com.rafaelleal.minhasferias.data_local.converters

import br.com.rafaelleal.minhasferias.data_local.db.registeredevents.entities.RegisteredEventEntity
import   br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import kotlin.reflect.full.memberProperties

inline fun <reified T : Any, reified R : Any> T.copy2(entityClass: Class<R>): R =
    with(R::class.constructors.toTypedArray().first()) {
        val propertiesByName = T::class.memberProperties.associateBy { it.name }
        callBy(args = parameters.associate { parameter ->
            parameter to propertiesByName[parameter.name]?.get(this@copy2)
        })
    }

fun RegisteredEvent.toEntity() = this.copy2(RegisteredEventEntity::class.java)
fun RegisteredEventEntity.toModel() = this.copy2(RegisteredEvent::class.java)
