package br.com.rafaelleal.minhasferias.presentation_events_friends.converters

import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.GetRegisteredEventWithFriendsUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventWithFriendsUiModel
import javax.inject.Inject

class GetRegisteredEventWithFriendsConverter @Inject constructor() :
    CommonResultConverter<GetRegisteredEventWithFriendsUseCase.Response, EventWithFriendsUiModel>() {
    override fun convertSuccess(
        data: GetRegisteredEventWithFriendsUseCase.Response
    ): EventWithFriendsUiModel {
        val selectedFriendIdList = data.listOfFriends.map { it.id }
        return EventWithFriendsUiModel(
            eventId = data.event.id,
            eventName = data.event.name,
            eventTime = "${data.event.day} - ${data.event.time}",
            friendList = data.allFriends.map { _friend ->
                EventFriendListItem(
                    id = _friend.id,
                    name = "${_friend.name} ${_friend.surname}",
                    selected = (_friend.id in selectedFriendIdList)
                )
            }.sortedBy { it.name }
        )
    }
}