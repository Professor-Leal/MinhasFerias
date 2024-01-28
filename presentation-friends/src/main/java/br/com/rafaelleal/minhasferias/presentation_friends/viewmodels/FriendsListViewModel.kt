package br.com.rafaelleal.minhasferias.presentation_friends.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetAllFriendsUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.SaveFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_friends.converters.GetAllFriendsUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.converters.SaveFriendUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val getAllFriendsUiConverter: GetAllFriendsUiConverter,
    private val saveFriendUseCase: SaveFriendUseCase,
    private val saveFriendUiConverter: SaveFriendUiConverter,
) : ViewModel() {

    private val _friendsListFlow =
        MutableStateFlow<UiState<FriendListModel>>(UiState.Loading)
    val friendsListFlow: StateFlow<UiState<FriendListModel>> =
        _friendsListFlow

    private val _saveFriendFlow =
        MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val saveFriendFlow: StateFlow<UiState<Unit>> =
        _saveFriendFlow

    fun loadFriends() {
        viewModelScope.launch {
            _friendsListFlow.value = UiState.Loading
            getAllFriendsUseCase.execute(GetAllFriendsUseCase.Request)
                .map {
                    getAllFriendsUiConverter.convert(it)
                }
                .collect {
                    _friendsListFlow.value = it
                }
        }
    }

    fun saveFriend(registeredEvent: Friend) {
        viewModelScope.launch {
            _saveFriendFlow.value = UiState.Loading
            saveFriendUseCase.execute(SaveFriendUseCase.Request(registeredEvent))
                .map {
                    saveFriendUiConverter.convert(it)
                }
                .collect {
                    _saveFriendFlow.value = it
                }
        }
    }

}