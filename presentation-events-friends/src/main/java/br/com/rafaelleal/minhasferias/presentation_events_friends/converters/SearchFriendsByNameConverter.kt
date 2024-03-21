package br.com.rafaelleal.minhasferias.presentation_events_friends.converters

import android.content.Context
import br.com.rafaelleal.minhasferias.domain.usecase.friends.SearchFriendsByNameUseCase
import br.com.rafaelleal.minhasferias.presentation_common.converters.CommonResultConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchFriendsByNameConverter @Inject constructor()
    : CommonResultConverter<SearchFriendsByNameUseCase.Response, List<EventFriendListItem>>() {
    override fun convertSuccess(
        data: SearchFriendsByNameUseCase.Response
    ): List<EventFriendListItem> {
        val allFriends = data.allFriends
        val listOfFriendIds = data.listOfFriends.map { it.id }

        return allFriends.map { _friend ->
            EventFriendListItem(
                id = _friend.id,
                name = "${_friend.name} ${_friend.surname}",
                selected = (_friend.id in listOfFriendIds)
            )
        }.sortedBy { it.name }
    }
}
