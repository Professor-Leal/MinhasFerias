package br.com.rafaelleal.minhasferias.presentation_friends.converters

import br.com.rafaelleal.minhasferias.domain.usecase.friends.SaveFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class SaveFriendUiConverter @Inject constructor() :
    CommonResultConverter<SaveFriendUseCase.Response, Unit>() {
    override fun convertSuccess(
        data: SaveFriendUseCase.Response
    ): Unit {
        return Unit
    }
}