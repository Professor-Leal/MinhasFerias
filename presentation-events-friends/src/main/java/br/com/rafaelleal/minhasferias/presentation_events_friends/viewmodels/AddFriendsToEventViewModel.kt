package br.com.rafaelleal.minhasferias.presentation_events_friends.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.GetRegisteredEventWithFriendsUseCase
import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.ChangeEventFriendRelationshipUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.SearchFriendsByNameUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_events_friends.converters.ChangeEventFriendRelationshipConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.converters.GetRegisteredEventWithFriendsConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.converters.SearchFriendsByNameConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventWithFriendsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendsToEventViewModel @Inject constructor(
    private val getRegisteredEventWithFriendsUseCase: GetRegisteredEventWithFriendsUseCase,
    private val getRegisteredEventWithFriendsConverter: GetRegisteredEventWithFriendsConverter,
    private val searchFriendsByNameUseCase: SearchFriendsByNameUseCase,
    private val searchFriendsByNameConverter: SearchFriendsByNameConverter,
    private val changeEventFriendRelationshipUseCase: ChangeEventFriendRelationshipUseCase,
    private val changeEventFriendRelationshipConverter: ChangeEventFriendRelationshipConverter
) : ViewModel() {

    private var eventId = 0L

    private val _changeEventFriendRelationshipFlow =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val changeEventFriendRelationshipFlow: StateFlow<UiState<Boolean>> =
        _changeEventFriendRelationshipFlow

    fun changeEventFriendRelationship(friendId: Long) {
        viewModelScope.launch {
            _changeEventFriendRelationshipFlow.value = UiState.Loading
            changeEventFriendRelationshipUseCase.execute(
                ChangeEventFriendRelationshipUseCase.Request(eventId = eventId, friendId = friendId)
            ).map {
                changeEventFriendRelationshipConverter.convert(it)
            }.collect {
                _changeEventFriendRelationshipFlow.value = it
            }
        }
    }


    // Referente ao load inicial:
    private val _resgisteredEventWithFriendsFlow =
        MutableStateFlow<UiState<EventWithFriendsUiModel>>(UiState.Loading)
    val resgisteredEventWithFriendsFlow: StateFlow<UiState<EventWithFriendsUiModel>> =
        _resgisteredEventWithFriendsFlow

    fun loadEventWithFriends(eventId: Long) {
        this.eventId = eventId
        viewModelScope.launch {
            _resgisteredEventWithFriendsFlow.value = UiState.Loading
            getRegisteredEventWithFriendsUseCase.execute(
                GetRegisteredEventWithFriendsUseCase.Request(eventId = eventId)
            ).map {
                getRegisteredEventWithFriendsConverter.convert(it)
            }.collect {
                _resgisteredEventWithFriendsFlow.value = it
                _filteredFriendListFlow.value = when (it) {
                    is UiState.Success -> UiState.Success(it.data.friendList)
                    is UiState.Loading -> UiState.Loading
                    is UiState.Error -> UiState.Error(it.errorMessage)
                }
            }
        }
    }

    // Referente à busca com filtro

    private val _filteredFriendListFlow =
        MutableStateFlow<UiState<List<EventFriendListItem>>>(UiState.Loading)
    val filteredFriendListFlow: StateFlow<UiState<List<EventFriendListItem>>> =
        _filteredFriendListFlow

    fun searchFriends(searchInput: String) {
        viewModelScope.launch {
            _filteredFriendListFlow.value = UiState.Loading
            searchFriendsByNameUseCase.execute(
                SearchFriendsByNameUseCase.Request(
                    searchInput = "%${searchInput}%",
                    eventId = this@AddFriendsToEventViewModel.eventId
                )
            ).map {
                searchFriendsByNameConverter.convert(it)
            }.collect {
                _filteredFriendListFlow.value = it
            }
        }
    }

    // Referente ao campo de busca:
    // Referencia: https://www.youtube.com/watch?v=jYJKX_7l9H4
    // Programador de Elite - [COMPOSE SEARCH] COMO CRIAR CAMPO DE BUSCA NA TOPBAR EM JETPACK COMPOSE NO ANDROID

    private val _currentSearchTextState: MutableState<String> = mutableStateOf("")
    val currentSearchTextState: State<String> = _currentSearchTextState

    fun setCurrentSearchText(value: String): Unit {
        _currentSearchTextState.value = value
    }


}