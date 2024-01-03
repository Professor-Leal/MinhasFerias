package br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels

import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.DeleteRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.GetRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.registeredEvents.UpdateRegisteredEventUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.DeleteRegisteredEventUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.GetRegisteredEventUiConverter
import br.com.rafaelleal.minhasferias.presentation_registered_events.converters.UpdateRegisteredEventUiConverter
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

class EditRegisteredEventViewModelTest {

    val dispatcher = UnconfinedTestDispatcher()

    private val getRegisteredEventUseCase by lazy { mock<GetRegisteredEventUseCase>() }
    private val getRegisteredEventUiConverter by lazy { mock<GetRegisteredEventUiConverter>() }

    private val updateRegisteredEventUseCase by lazy { mock<UpdateRegisteredEventUseCase>() }
    private val updateRegisteredEventUiConverter by lazy { mock<UpdateRegisteredEventUiConverter>() }

    private val deleteRegisteredEventUseCase by lazy { mock<DeleteRegisteredEventUseCase>() }
    private val deleteRegisteredEventUiConverter by lazy { mock<DeleteRegisteredEventUiConverter>() }

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val viewModel by lazy {
        EditRegisteredEventViewModel(
            getRegisteredEventUseCase,
            getRegisteredEventUiConverter,
            updateRegisteredEventUseCase,
            updateRegisteredEventUiConverter,
            deleteRegisteredEventUseCase,
            deleteRegisteredEventUiConverter
        )
    }

    @Test
    fun `should load event from given id`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.resgisteredEventFlow.value)

        val usecaseResponse = mock<GetRegisteredEventUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)
        val itemMock = mock<RegisteredEvent>()
        val uiState = UiState.Success(itemMock)
        val registeredEventId = 1L

        whenever(
            getRegisteredEventUseCase.execute(GetRegisteredEventUseCase.Request(registeredEventId))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            getRegisteredEventUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.loadRegisteredEvent(registeredEventId)
        assertEquals(uiState, viewModel.resgisteredEventFlow.value)

    }

    @Test
    fun `should update registered event when asked`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.updateRegisteredEventFlow.value)
        val usecaseResponse = mock<UpdateRegisteredEventUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)

        val itemMock = mock<RegisteredEvent>()
        val uiState = UiState.Success(true)

        whenever(
            updateRegisteredEventUseCase.execute(UpdateRegisteredEventUseCase.Request(itemMock))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            updateRegisteredEventUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.updateRegisteredEvent(itemMock)
        assertEquals(uiState, viewModel.updateRegisteredEventFlow.value)

    }

    @Test
    fun `should delete registered event when asked`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.updateRegisteredEventFlow.value)
        val usecaseResponse = mock<DeleteRegisteredEventUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)

        val itemId = 1L
        val uiState = UiState.Success(true)

        whenever(
            deleteRegisteredEventUseCase.execute(DeleteRegisteredEventUseCase.Request(itemId))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            deleteRegisteredEventUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.deleteRegisteredEvent(itemId)
        assertEquals(uiState, viewModel.deleteRegisteredEventFlow.value)

    }

}