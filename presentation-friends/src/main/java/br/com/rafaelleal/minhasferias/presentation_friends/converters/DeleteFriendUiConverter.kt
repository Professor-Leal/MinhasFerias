package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.usecase.friends.DeleteFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class DeleteFriendUiConverter  @Inject constructor() :
    CommonResultConverter<DeleteFriendUseCase.Response, Boolean>() {
    override fun convertSuccess(
        data: DeleteFriendUseCase.Response
    ): Boolean {
        return data.result
    }
}