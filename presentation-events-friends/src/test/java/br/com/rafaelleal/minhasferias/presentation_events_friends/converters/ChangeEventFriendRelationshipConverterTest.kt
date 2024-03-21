package br.com.rafaelleal.minhasferias.presentation_events_friends.converters

import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.ChangeEventFriendRelationshipUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class ChangeEventFriendRelationshipConverterTest {

    private val converter = ChangeEventFriendRelationshipConverter()

    @Test
    fun testConvertSuccess() {
        val data = ChangeEventFriendRelationshipUseCase.Response(
            true
        )
        val expectedResult = true

        val convertResult = converter.convertSuccess(data)
        assertEquals(expectedResult, convertResult)
    }

}