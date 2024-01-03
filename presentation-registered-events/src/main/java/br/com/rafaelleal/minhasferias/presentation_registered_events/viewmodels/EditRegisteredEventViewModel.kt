package br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.DeleteRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.UpdateRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.DeleteRegisteredEventUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.GetRegisteredEventUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.UpdateRegisteredEventUiConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditRegisteredEventViewModel @Inject constructor(
    private val getRegisteredEventUseCase: GetRegisteredEventUseCase,
    private val getRegisteredEventUiConverter: GetRegisteredEventUiConverter,
    private val updateRegisteredEventUseCase: UpdateRegisteredEventUseCase,
    private val updateRegisteredEventUiConverter: UpdateRegisteredEventUiConverter,
    private val deleteRegisteredEventUseCase: DeleteRegisteredEventUseCase,
    private val deleteRegisteredEventUiConverter: DeleteRegisteredEventUiConverter
) : ViewModel() {

    val _updateRegisteredEventFlow =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val updateRegisteredEventFlow: StateFlow<UiState<Boolean>> =
        _updateRegisteredEventFlow

    val _deleteRegisteredEventFlow =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteRegisteredEventFlow: StateFlow<UiState<Boolean>> =
        _deleteRegisteredEventFlow

    private val _resgisteredEventFlow =
        MutableStateFlow<UiState<RegisteredEvent>>(UiState.Loading)
    val resgisteredEventFlow: StateFlow<UiState<RegisteredEvent>> =
        _resgisteredEventFlow

    fun loadRegisteredEvent(id: Long) {
        viewModelScope.launch {
            _resgisteredEventFlow.value = UiState.Loading
            getRegisteredEventUseCase.execute(GetRegisteredEventUseCase.Request(id))
                .map {
                    getRegisteredEventUiConverter.convert(it)
                }
                .collect {
                    _resgisteredEventFlow.value = it
                }
        }
    }

    fun updateRegisteredEvent(registeredEvent: RegisteredEvent) {
        viewModelScope.launch {
            _updateRegisteredEventFlow.value = UiState.Loading
            updateRegisteredEventUseCase.execute(
                UpdateRegisteredEventUseCase.Request(
                    registeredEvent
                )
            )
                .map {
                    updateRegisteredEventUiConverter.convert(it)
                }.collect {
                    _updateRegisteredEventFlow.value = it
                }
        }
    }

    fun deleteRegisteredEvent(id: Long) {
        viewModelScope.launch {
            _deleteRegisteredEventFlow.value = UiState.Loading
            deleteRegisteredEventUseCase.execute(
                DeleteRegisteredEventUseCase.Request(
                    id
                )
            ).map {
                deleteRegisteredEventUiConverter.convert(it)
            }.collect {
                _deleteRegisteredEventFlow.value = it
            }
        }
    }


}