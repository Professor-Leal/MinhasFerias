package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class GetFriendUiConverter @Inject constructor() :
    CommonResultConverter<GetFriendUseCase.Response, Friend>() {
    override fun convertSuccess(
        data: GetFriendUseCase.Response
    ): Friend {
        return data.event
    }
}