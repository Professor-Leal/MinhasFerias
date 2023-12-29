package br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.GetRegisteredEventUiConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRegisteredEventViewModel @Inject constructor(
    private val getRegisteredEventUseCase: GetRegisteredEventUseCase,
    private val getRegisteredEventConverter: GetRegisteredEventUiConverter
) : ViewModel() {

    private val _resgisteredEventFlow =
        MutableStateFlow<UiState<RegisteredEvent>>(UiState.Loading)
    val resgisteredEventFlow: StateFlow<UiState<RegisteredEvent>> =
        _resgisteredEventFlow

    fun getRegisteredEvent(id: Long) {
        viewModelScope.launch {
            _resgisteredEventFlow.value = UiState.Loading
            getRegisteredEventUseCase.execute(
                GetRegisteredEventUseCase.Request(id)
            ).map {
                getRegisteredEventConverter.convert(it)
            }.collect {
                _resgisteredEventFlow.value = it
            }
        }
    }

}