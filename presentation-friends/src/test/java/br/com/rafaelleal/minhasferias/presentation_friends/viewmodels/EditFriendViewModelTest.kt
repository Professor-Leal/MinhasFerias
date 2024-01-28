package br.com.rafaelleal.minhasferias.presentation_friends.viewmodels

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.domain.usecase.friends.DeleteFriendUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetFriendUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.UpdateFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_friends.converters.DeleteFriendUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.converters.GetFriendUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.converters.UpdateFriendUiConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class EditFriendViewModelTest {

    val dispatcher = UnconfinedTestDispatcher()

    private val getFriendUseCase by lazy { mock<GetFriendUseCase>() }
    private val getFriendUiConverter by lazy { mock<GetFriendUiConverter>() }

    private val updateFriendUseCase by lazy { mock<UpdateFriendUseCase>() }
    private val updateFriendUiConverter by lazy { mock<UpdateFriendUiConverter>() }

    private val deleteFriendUseCase by lazy { mock<DeleteFriendUseCase>() }
    private val deleteFriendUiConverter by lazy { mock<DeleteFriendUiConverter>() }

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val viewModel by lazy {
        EditFriendViewModel(
            getFriendUseCase,
            getFriendUiConverter,
            updateFriendUseCase,
            updateFriendUiConverter,
            deleteFriendUseCase,
            deleteFriendUiConverter
        )
    }

    @Test
    fun `should load friend from given id`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.friendFlow.value)

        val usecaseResponse = mock<GetFriendUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)
        val itemMock = mock<Friend>()
        val uiState = UiState.Success(itemMock)
        val registeredEventId = 1L

        whenever(
            getFriendUseCase.execute(GetFriendUseCase.Request(registeredEventId))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            getFriendUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.loadFriend(registeredEventId)
        assertEquals(uiState, viewModel.friendFlow.value)

    }

    @Test
    fun `should update friend when asked`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.updateFriendFlow.value)
        val usecaseResponse = mock<UpdateFriendUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)

        val itemMock = mock<Friend>()
        val uiState = UiState.Success(true)

        whenever(
            updateFriendUseCase.execute(UpdateFriendUseCase.Request(itemMock))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            updateFriendUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.updateFriend(itemMock)
        assertEquals(uiState, viewModel.updateFriendFlow.value)

    }

    @Test
    fun `should delete friend when asked`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.updateFriendFlow.value)
        val usecaseResponse = mock<DeleteFriendUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)

        val itemId = 1L
        val uiState = UiState.Success(true)

        whenever(
            deleteFriendUseCase.execute(DeleteFriendUseCase.Request(itemId))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            deleteFriendUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.deleteFriend(itemId)
        assertEquals(uiState, viewModel.deleteFriendFlow.value)

    }

}