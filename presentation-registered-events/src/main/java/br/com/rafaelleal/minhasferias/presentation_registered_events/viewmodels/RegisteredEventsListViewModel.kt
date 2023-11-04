package br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.GetAllRegisteredEventsUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.SaveRegisteredEventUiConverter
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.SaveRegisteredEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisteredEventsListViewModel @Inject constructor(
    private val getAllRegisteredEventsUseCase: GetAllRegisteredEventsUseCase,
    private val getAllRegisteredEventsUiConverter: GetAllRegisteredEventsUiConverter,
    private val saveRegisteredEventUseCase: SaveRegisteredEventUseCase,
    private val saveRegisteredEventUiConverter: SaveRegisteredEventUiConverter,
) : ViewModel() {
    private val _resgisteredEventsListFlow =
        MutableStateFlow<UiState<RegisteredEventsListModel>>(UiState.Loading)
    val resgisteredEventsListFlow: StateFlow<UiState<RegisteredEventsListModel>> =
        _resgisteredEventsListFlow


    private val _saveRegisteredEventFlow =
        MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val saveRegisteredEventFlow: StateFlow<UiState<Unit>> =
        _saveRegisteredEventFlow

    fun loadRegisteredEvents() {
        viewModelScope.launch {
            _resgisteredEventsListFlow.value = UiState.Loading
            getAllRegisteredEventsUseCase.execute(GetAllRegisteredEventsUseCase.Request)
                .map {
                    getAllRegisteredEventsUiConverter.convert(it)
                }
                .collect {
                    _resgisteredEventsListFlow.value = it
                }
        }
    }

    fun saveRegisteredEvent(registeredEvent: RegisteredEvent) {
        viewModelScope.launch {
            _saveRegisteredEventFlow.value = UiState.Loading
            saveRegisteredEventUseCase.execute(SaveRegisteredEventUseCase.Request(registeredEvent))
                .map {
                    saveRegisteredEventUiConverter.convert(it)
                }
                .collect {
                    _saveRegisteredEventFlow.value = it
                }
        }
    }
}