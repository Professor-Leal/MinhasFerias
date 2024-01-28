package br.com.rafaelleal.minhasferias.presentation_friends.converters

import android.content.Context
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetAllFriendsUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import br.com.rafaelleal.minhasferias.presentation_friends.R
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAllFriendsUiConverter @Inject constructor(
    @ApplicationContext private val context: Context
) : CommonResultConverter<GetAllFriendsUseCase.Response, FriendListModel>() {
    override fun convertSuccess(
        data: GetAllFriendsUseCase.Response
    ): FriendListModel {
        return FriendListModel(
            headerText = context.getString(
                R.string.friends_list_header,
            ),
            items = data.list.map { _friend ->
                FriendListItemModel(
                    name = _friend.name,
                    surname = _friend.surname,
                    phoneNumber = _friend.phoneNumber,
                    documentNumber = _friend.documentNumber,
                    id = _friend.id
                )
            }
        )
    }
}