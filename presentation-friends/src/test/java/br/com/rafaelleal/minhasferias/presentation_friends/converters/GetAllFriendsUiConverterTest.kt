package br.com.rafaelleal.minhasferias.presentation_friends.converters

import android.content.Context
import br.com.rafaelleal.minhasferias.presentation_friends.R
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetAllFriendsUseCase
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListModel
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllFriendsUiConverterTest {
    private val context = mock<Context>()
    private val converter = GetAllFriendsUiConverter(context)
    
    val item = listOf(Friend(name = "Name", surname = "Surname", phoneNumber = "555-0000", documentNumber = "123.123.123-12", id = 1L))
    
    val itemsModel = item.map { _friend ->
        FriendListItemModel(
            name=_friend.name , id=_friend.id, surname = _friend.surname, phoneNumber = _friend.phoneNumber, documentNumber = _friend.documentNumber
        )
    }

    @Test
    fun testConvertSuccess() {
        val response = GetAllFriendsUseCase.Response(item)
        val expectedResult = FriendListModel(
            headerText = "Friends",
            items = itemsModel
        )
        whenever(
            context.getString(R.string.friends_list_header)
        ).thenReturn("Friends")

        val convertResult = converter.convertSuccess(response)
        assertEquals(expectedResult, convertResult)
    }
}