package br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels

import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetAllRegisteredEventsUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.converters.GetAllRegisteredEventsUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.RegisteredEventsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RegisteredEventsListViewModelTest {
    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val getAllRegisteredEventsUseCase: GetAllRegisteredEventsUseCase by lazy { mock() }
    private val getAllRegisteredEventsUiConverter: GetAllRegisteredEventsUiConverter by lazy { mock() }

    private val viewModel by lazy {
        RegisteredEventsListViewModel(
            getAllRegisteredEventsUseCase,
            getAllRegisteredEventsUiConverter
        )
    }

    @Test
    fun should_Register_WhenLoadRegisteredEvents(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.resgisteredEventsListFlow.value)

        val usecaseResponse = mock<GetAllRegisteredEventsUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)
        val listModelMock = mock<RegisteredEventsListModel>()
        val uiState = UiState.Success(listModelMock)

        whenever(
            getAllRegisteredEventsUseCase.execute(GetAllRegisteredEventsUseCase.Request)
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            getAllRegisteredEventsUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.loadRegisteredEvents()
        assertEquals(uiState, viewModel.resgisteredEventsListFlow.value)

    }

}