package br.com.rafaelleal.minhasferias.presentation_common.converters

import android.content.res.Resources.NotFoundException
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import   br.com.rafaelleal.minhasferias.domain.sealed.Result
import   br.com.rafaelleal.minhasferias.domain.sealed.UseCaseException
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CommonResultConverterTest {
    private val converter = object : CommonResultConverter<String, String>() {
        override fun convertSuccess(data: String): String {
            return "result${data}"
        }
    }

    // Mockito fails to mock sealed class in java 17
     @Test
     fun testConvertError() {
         val errorMessage = "Endereço não encontrado"
         val exception = UseCaseException.UnknownException(Exception(errorMessage))
         val errorResult = Result.Error(exception)
         val result = converter.convert(errorResult)
         assertEquals(UiState.Error(exception.localizedMessage.orEmpty()), result)
     }

    @Test
    fun testConvertSuccess() {
        val data = "data"
        val successResult = Result.Success(data)
        val result = converter.convert(successResult)
        assertEquals(UiState.Success("result${data}"), result)
    }
}
