package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetFriendUseCase
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock

class GetFriendUiConverterTest {
    private val converter = GetFriendUiConverter()

    @Test
    fun testConvertSuccess() {
        val itemMock = mock<Friend>()
        val response = GetFriendUseCase.Response(itemMock)
        val expectedResult = itemMock

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}