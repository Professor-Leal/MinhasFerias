package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.usecase.friends.UpdateFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class UpdateFriendUiConverter @Inject constructor() :
    CommonResultConverter<UpdateFriendUseCase.Response, Boolean>() {
    override fun convertSuccess(
        data: UpdateFriendUseCase.Response
    ): Boolean {
        return data.result
    }
}