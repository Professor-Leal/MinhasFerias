package br.com.rafaelleal.minhasferias.presentation_registered_events.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.converters.GetAllRegisteredEventsUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisteredEventsListViewModel @Inject constructor(
    private val useCase: GetAllRegisteredEventsUseCase,
    private val converter: GetAllRegisteredEventsUiConverter,
) : ViewModel() {

    private val _resgisteredEventsListFlow =
        MutableStateFlow<UiState<RegisteredEventsListModel>>(UiState.Loading)
    val resgisteredEventsListFlow: StateFlow<UiState<RegisteredEventsListModel>> = _resgisteredEventsListFlow

    fun loadRegisteredEvents() {
        viewModelScope.launch {
            useCase.execute(GetAllRegisteredEventsUseCase.Request)
                .map {
                    converter.convert(it)
                }
                .collect {
                    _resgisteredEventsListFlow.value = it
                }
        }
    }
}