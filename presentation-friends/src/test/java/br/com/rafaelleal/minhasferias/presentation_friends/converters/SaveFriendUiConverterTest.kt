package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.usecase.friends.SaveFriendUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class SaveFriendUiConverterTest {
    private val converter = SaveFriendUiConverter()

    @Test
    fun testConvertSuccess() {
        val response = SaveFriendUseCase.Response
        val expectedResult = Unit

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}