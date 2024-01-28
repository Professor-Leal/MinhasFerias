package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.usecase.friends.DeleteFriendUseCase
import org.junit.Assert.*
import org.junit.Test

class DeleteFriendUiConverterTest {
    private val converter = DeleteFriendUiConverter()

    @Test
    fun testConvertSuccess() {
        val itemMock = true
        val response = DeleteFriendUseCase.Response(itemMock)
        val expectedResult = true

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}