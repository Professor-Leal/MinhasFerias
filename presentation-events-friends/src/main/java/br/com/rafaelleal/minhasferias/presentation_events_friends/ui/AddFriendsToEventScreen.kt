package br.com.rafaelleal.minhasferias.presentation_events_friends.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.screens.icons.CheckIcon
import br.com.rafaelleal.minhasferias.presentation_common.screens.icons.EmptyCheckIcon
import br.com.rafaelleal.minhasferias.presentation_common.screens.inputs.SearchTextField
import br.com.rafaelleal.minhasferias.presentation_common.screens.texts.ParagraphBoldText
import br.com.rafaelleal.minhasferias.presentation_common.screens.texts.ParagraphText
import br.com.rafaelleal.minhasferias.presentation_common.screens.texts.TitleText
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_events_friends.R
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventFriendListItem
import br.com.rafaelleal.minhasferias.presentation_events_friends.models.EventWithFriendsUiModel
import br.com.rafaelleal.minhasferias.presentation_events_friends.viewmodels.AddFriendsToEventViewModel


val friend01Mock = EventFriendListItem(
    id = 1L, "John Doe", true
)

val friend02Mock = EventFriendListItem(
    id = 2L, "Jane Doe", true
)

val friend03Mock = EventFriendListItem(
    id = 3L, "John Travolta", false
)

val friend04Mock = EventFriendListItem(
    id = 4L, "Deny Devito", false
)
val friend05Mock = EventFriendListItem(
    id = 5L, "Keanu Reeves", true
)

val friendListMock = listOf(
    friend01Mock,
    friend02Mock,
    friend03Mock,
    friend04Mock,
    friend05Mock,
).sortedBy { it.name }

val itemMock = EventWithFriendsUiModel(
    eventId = 1L,
    eventName = "Evento 01",
    eventTime = "12:00 - 01/01/2024",
    friendList = friendListMock
)

@Composable
fun AddFriendsToEventScreen(
    eventId: Long,
    navController: NavHostController,
    viewModel: AddFriendsToEventViewModel
) {
    val currentSearchText by viewModel.currentSearchTextState

    var uiState: UiState<EventWithFriendsUiModel> by remember {
        mutableStateOf(UiState.Loading)
    }
    var filteredFriendListUiState: UiState<List<EventFriendListItem>> by remember {
        mutableStateOf(UiState.Loading)
    }
    var changeEventFriendRelationshipUiState: UiState<Boolean> by remember {
        mutableStateOf(UiState.Loading)
    }
    LaunchedEffect(true) {
        viewModel.loadEventWithFriends(eventId)
    }
    viewModel.resgisteredEventWithFriendsFlow.collectAsState().value.let { state ->
        uiState = state
        if (uiState is UiState.Success) {
            filteredFriendListUiState =
                UiState.Success((uiState as UiState.Success).data.friendList)
        }
    }
    viewModel.filteredFriendListFlow.collectAsState().value.let { state ->
        filteredFriendListUiState = state
    }
    viewModel.changeEventFriendRelationshipFlow.collectAsState().value.let { state ->
        changeEventFriendRelationshipUiState = state
    }

    CommonScreen(state = uiState) {
        AddFriendsToEventBody(
            it,
            filteredFriendListUiState,
            currentSearchText = currentSearchText,
            onSearchTextChanged = { viewModel.setCurrentSearchText(value = it) },
            onSearchDispatched = { viewModel.searchFriends(searchInput = it) },
            onFriendClick = { friendId: Long -> viewModel.changeEventFriendRelationship(friendId) }
        )
    }
}

@Composable
fun AddFriendsToEventBody(
    event: EventWithFriendsUiModel,
    filteredList: UiState<List<EventFriendListItem>>,
    currentSearchText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onSearchDeactivated: () -> Unit = {},
    onSearchDispatched: (String) -> Unit = {},
    onFriendClick: (friendId: Long) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        EventCard(event, modifier = Modifier.wrapContentSize())
        SearchTextField(
            currentSearchText = currentSearchText,
            onSearchTextChanged = onSearchTextChanged,
            onSearchDeactivated = onSearchDeactivated,
            onSearchDispatched = onSearchDispatched,
            placeHolder = stringResource(R.string.search_a_friend)
        )
        CommonScreen(state = filteredList) {
            FriendFromEventList(it, onFriendClick)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun AddFriendsToEventScreenPreview() {
    AddFriendsToEventBody(itemMock, UiState.Success(friendListMock))
}


@Composable
fun EventCard(event: EventWithFriendsUiModel, modifier: Modifier) {
    Card(
        elevation = 8.dp, modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .testTag("EventCard")
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Blue90)
        ) {
            TitleText(event.eventName)
            ParagraphText(text = event.eventTime, modifier = modifier)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    EventCard(
        itemMock, Modifier
    )
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        currentSearchText = "",
        placeHolder = stringResource(R.string.search_a_friend)
    )
}

@Preview
@Composable
fun SearchTextFieldWithtextPreview() {
    SearchTextField(
        currentSearchText = "currentSearchText",
        placeHolder = stringResource(R.string.search_a_friend)
    )
}

@Composable
fun FriendFromEvent(
    friend: EventFriendListItem,
    onFriendClick: (friendId: Long) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Blue90)
            .wrapContentHeight(Alignment.CenterVertically)
            .heightIn(0.dp, 60.dp)
            .testTag("FriendFromEvent-${friend.id}")
            .clickable { onFriendClick(friend.id) }
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "document icon",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(24.dp),
            tint = MaterialTheme.colors.primaryVariant
        )
        ParagraphBoldText(text = friend.name)

        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        if(friend.selected){
            CheckIcon(modifier = Modifier)
        } else {
            EmptyCheckIcon(modifier = Modifier)
        }
    }
}

@Preview
@Composable
fun FriendFromEventPreview() {
    FriendFromEvent(friend01Mock)
}

@Composable
fun FriendFromEventList(
    friendList: List<EventFriendListItem> = emptyList(),
    onFriendClick: (friendId: Long) -> Unit = {}
) {
    LazyColumn(modifier = Modifier) {
        items(friendList) { item ->
            FriendFromEvent(item, onFriendClick)
        }
    }
}

@Preview
@Composable
fun FriendFromEventListPreview() {
    FriendFromEventList(friendListMock)
}