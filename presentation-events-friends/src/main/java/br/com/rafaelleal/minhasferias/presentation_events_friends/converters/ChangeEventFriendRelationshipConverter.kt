package br.com.rafaelleal.minhasferias.presentation_events_friends.converters

import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.ChangeEventFriendRelationshipUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import javax.inject.Inject

class ChangeEventFriendRelationshipConverter @Inject constructor() :
    CommonResultConverter<ChangeEventFriendRelationshipUseCase.Response, Boolean>() {
    override fun convertSuccess(
        data: ChangeEventFriendRelationshipUseCase.Response
    ): Boolean {
        return data.result
    }
}