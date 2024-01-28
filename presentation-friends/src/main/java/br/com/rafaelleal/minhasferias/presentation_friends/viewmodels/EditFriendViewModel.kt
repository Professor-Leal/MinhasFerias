package br.com.rafaelleal.minhasferias.presentation_friends.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.usecase.friends.DeleteFriendUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetFriendUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.UpdateFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_friends.converters.DeleteFriendUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.converters.GetFriendUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.converters.UpdateFriendUiConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditFriendViewModel @Inject constructor(
    private val getFriendUseCase: GetFriendUseCase,
    private val getFriendUiConverter: GetFriendUiConverter,
    private val updateFriendUseCase: UpdateFriendUseCase,
    private val updateFriendUiConverter: UpdateFriendUiConverter,
    private val deleteFriendUseCase: DeleteFriendUseCase,
    private val deleteFriendUiConverter: DeleteFriendUiConverter
) : ViewModel() {

    val _updateFriendFlow =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val updateFriendFlow: StateFlow<UiState<Boolean>> =
        _updateFriendFlow

    val _deleteFriendFlow =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteFriendFlow: StateFlow<UiState<Boolean>> =
        _deleteFriendFlow

    private val _friendFlow =
        MutableStateFlow<UiState<Friend>>(UiState.Loading)
    val friendFlow: StateFlow<UiState<Friend>> =
        _friendFlow

    fun loadFriend(id: Long) {
        viewModelScope.launch {
            _friendFlow.value = UiState.Loading
            getFriendUseCase.execute(GetFriendUseCase.Request(id))
                .map {
                    getFriendUiConverter.convert(it)
                }
                .collect {
                    _friendFlow.value = it
                }
        }
    }

    fun updateFriend(registeredEvent: Friend) {
        viewModelScope.launch {
            _updateFriendFlow.value = UiState.Loading
            updateFriendUseCase.execute(
                UpdateFriendUseCase.Request(
                    registeredEvent
                )
            )
                .map {
                    updateFriendUiConverter.convert(it)
                }.collect {
                    _updateFriendFlow.value = it
                }
        }
    }

    fun deleteFriend(id: Long) {
        viewModelScope.launch {
            _deleteFriendFlow.value = UiState.Loading
            deleteFriendUseCase.execute(
                DeleteFriendUseCase.Request(
                    id
                )
            ).map {
                deleteFriendUiConverter.convert(it)
            }.collect {
                _deleteFriendFlow.value = it
            }
        }
    }


}