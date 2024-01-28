package br.com.rafaelleal.minhasferias.presentation_friends.viewmodels

import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetAllFriendsUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.SaveFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_friends.converters.GetAllFriendsUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.converters.SaveFriendUiConverter
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@OptIn(ExperimentalCoroutinesApi::class)
class FriendsListViewModelTest{

    val dispatcher = UnconfinedTestDispatcher()
    private val getAllFriendsUseCase: GetAllFriendsUseCase by lazy { mock() }
    private val getAllFriendsUiConverter: GetAllFriendsUiConverter by lazy { mock() }
    private val saveFriendUseCase: SaveFriendUseCase by lazy { mock() }
    private val saveFriendUiConverter: SaveFriendUiConverter by lazy { mock() }

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val viewModel by lazy {
        FriendsListViewModel(
            getAllFriendsUseCase,
            getAllFriendsUiConverter,
            saveFriendUseCase,
            saveFriendUiConverter,
        )
    }

    @Test
    fun should_Register_WhenLoadFriends(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.friendsListFlow.value)

        val usecaseResponse = mock<GetAllFriendsUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)
        val listModelMock = mock<FriendListModel>()
        val uiState = UiState.Success(listModelMock)

        whenever(
            getAllFriendsUseCase.execute(GetAllFriendsUseCase.Request)
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            getAllFriendsUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.loadFriends()
        assertEquals(uiState, viewModel.friendsListFlow.value)

    }

    @Test
    fun should_Save_WhenSaveIsRequired(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.saveFriendFlow.value)
        val item = mock<Friend>()
        val usecaseResponse = mock<SaveFriendUseCase.Response>()
        val usecaseResult = Result.Success(usecaseResponse)
        val unitMock = mock<Unit>()
        val uiState = UiState.Success(unitMock)
        whenever(
            saveFriendUseCase.execute(SaveFriendUseCase.Request(item))
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            saveFriendUiConverter.convert(usecaseResult)
        ).thenReturn(uiState)
        viewModel.saveFriend(item)
        assertEquals(uiState, viewModel.saveFriendFlow.value)
    }




}