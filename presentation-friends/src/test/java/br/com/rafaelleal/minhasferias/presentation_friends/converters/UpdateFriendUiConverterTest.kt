package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.usecase.friends.UpdateFriendUseCase
import org.junit.Assert.*
import org.junit.Test

class UpdateFriendUiConverterTest {
    private val converter = UpdateFriendUiConverter()

    @Test
    fun testConvertSuccess() {
        val itemMock = true
        val response = UpdateFriendUseCase.Response(itemMock)
        val expectedResult = true

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}