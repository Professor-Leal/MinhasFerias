package br.com.rafaelleal.minhasferias.presentation_events_friends.viewmodels

import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.ChangeEventFriendRelationshipUseCase
import br.com.rafaelleal.minhasferias.domain.events_friends.usecase.GetRegisteredEventWithFriendsUseCase
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.domain.usecase.friends.DeleteFriendUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.GetFriendUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.SearchFriendsByNameUseCase
import br.com.rafaelleal.minhasferias.domain.usecase.friends.UpdateFriendUseCase
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_events_friends.converters.ChangeEventFriendRelationshipConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.converters.GetRegisteredEventWithFriendsConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.converters.SearchFriendsByNameConverter
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventWithFriendsUiModel
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
import java.time.LocalDateTime

class AddFriendsToEventViewModelTest {
    val dispatcher = UnconfinedTestDispatcher()

    private val getRegisteredEventWithFriendsUseCase by lazy { mock<GetRegisteredEventWithFriendsUseCase>() }
    private val getRegisteredEventWithFriendsConverter by lazy { mock<GetRegisteredEventWithFriendsConverter>() }

    private val searchFriendsByNameUseCase by lazy { mock<SearchFriendsByNameUseCase>() }
    private val searchFriendsByNameConverter by lazy { mock<SearchFriendsByNameConverter>() }

    private val changeEventFriendRelationshipUseCase by lazy { mock<ChangeEventFriendRelationshipUseCase>() }
    private val changeEventFriendRelationshipConverter by lazy { mock<ChangeEventFriendRelationshipConverter>() }


    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val viewModel by lazy {
        AddFriendsToEventViewModel(
            getRegisteredEventWithFriendsUseCase,
            getRegisteredEventWithFriendsConverter,
            searchFriendsByNameUseCase,
            searchFriendsByNameConverter,
            changeEventFriendRelationshipUseCase,
            changeEventFriendRelationshipConverter
        )
    }


    val event01 = RegisteredEvent(
        "Evento 01",
        "Endereço",
        "12:00",
        "01/01/2023",
        LocalDateTime.of(2023, 1, 1, 12, 0),
        1
    )
    val friend01 = Friend(
        name = "Name",
        surname = "Surname",
        phoneNumber = "555-0000",
        documentNumber = "123.123.123-23",
        id = 1L
    )
    val friend02 = Friend(
        name = "Name2",
        surname = "Surname2",
        phoneNumber = "555-0001",
        documentNumber = "123.123.123-24",
        id = 2L
    )

    val listOfFriends = listOf(friend01, friend02)

    val eventFriendListItem01 = EventFriendListItem(
        id = friend01.id, name = "${friend01.name} ${friend01.surname}", selected = false
    )

    val eventFriendListItem02 = EventFriendListItem(
        id = friend02.id, name = "${friend02.name} ${friend02.surname}", selected = false
    )

    val listOfEventFriendListItem = listOf(eventFriendListItem01, eventFriendListItem02)

    val eventWithFriendsUiModel =
        EventWithFriendsUiModel(
            eventId = event01.id,
            eventName = event01.name,
            eventTime = "${event01.day} - ${event01.time}",
            friendList = listOfEventFriendListItem
        )

    @Test
    fun `should load event with friends given event id`(): Unit = runBlocking {

        assertEquals(UiState.Loading, viewModel.resgisteredEventWithFriendsFlow.value)

        val usecaseResponse = GetRegisteredEventWithFriendsUseCase.Response(
            event = event01, listOfFriends = listOf(), allFriends = listOfFriends
        )
        val usecaseResult = Result.Success(usecaseResponse)

        val uiState = UiState.Success(eventWithFriendsUiModel)
        val uiStateFilteredFriends = UiState.Success(listOfEventFriendListItem)

        whenever(
            getRegisteredEventWithFriendsUseCase.execute(
                GetRegisteredEventWithFriendsUseCase.Request(1L)
            )
        ).thenReturn(flowOf(usecaseResult))

        whenever(
            getRegisteredEventWithFriendsConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.loadEventWithFriends(event01.id)
        assertEquals(uiState, viewModel.resgisteredEventWithFriendsFlow.value)
        assertEquals(uiStateFilteredFriends, viewModel.filteredFriendListFlow.value)

    }

    @Test
    fun `should change the relationship of friend clicked`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.changeEventFriendRelationshipFlow.value)

        val usecaseResponse = ChangeEventFriendRelationshipUseCase.Response(true)
        val usecaseResult = Result.Success(usecaseResponse)
        val uiState = UiState.Success(true)

        // eventId é zero até ser carregado o evento
        whenever(
            changeEventFriendRelationshipUseCase.execute(
                ChangeEventFriendRelationshipUseCase.Request(0L, 1L)
            )
        ).thenReturn(flowOf(usecaseResult))

        whenever(
            changeEventFriendRelationshipConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.changeEventFriendRelationship(1L)
        assertEquals(uiState, viewModel.changeEventFriendRelationshipFlow.value)

    }

    @Test
    fun `should filter friends by name`(): Unit = runBlocking {
        assertEquals(UiState.Loading, viewModel.filteredFriendListFlow.value)
        val usecaseResponse = SearchFriendsByNameUseCase.Response(listOf(friend02), listOfFriends)
        val usecaseResult = Result.Success(usecaseResponse)
        val uiState = UiState.Success(listOf(eventFriendListItem02))

        // eventId é zero até ser carregado o evento
        whenever(
            searchFriendsByNameUseCase.execute(
                SearchFriendsByNameUseCase.Request("%Name2%" , 0L)
            )
        ).thenReturn(flowOf(usecaseResult))
        whenever(
            searchFriendsByNameConverter.convert(usecaseResult)
        ).thenReturn(uiState)

        viewModel.searchFriends("Name2")
        assertEquals(uiState, viewModel.filteredFriendListFlow.value)

    }
}