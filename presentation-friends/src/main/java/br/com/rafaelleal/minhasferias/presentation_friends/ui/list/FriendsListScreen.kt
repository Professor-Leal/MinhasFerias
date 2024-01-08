package br.com.rafaelleal.minhasferias.presentation_friends.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListModel


internal val itemMock01 = FriendListItemModel(
    name = "John",
    surname = "Doe",
    phoneNumber = "555-0001",
    documentNumber = "111.111.111-11",
    id = 1
)
internal val itemMock02 = FriendListItemModel(
    name = "Jane",
    surname = "Doe",
    phoneNumber = "555-0002",
    documentNumber = "111.111.111-22",
    id = 2
)
internal val itemMock03 = FriendListItemModel(
    name = "Bob",
    surname = "Uncle",
    phoneNumber = "555-0003",
    documentNumber = "111.111.111-33",
    id = 3
)
internal val itemsMock: List<FriendListItemModel> = listOf(
    itemMock01, itemMock02, itemMock03
)

internal val listModelMock = FriendListModel("Friends Preview", itemsMock)


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FriendsListScreen(
//    viewModel: FriendsListViewModel,
//    navController: NavHostController,
    onClickAddNewFriend: () -> Unit = {},
) {
    /*var uiState: UiState<FriendListModel> by remember {
        mutableStateOf(UiState.Loading)
    }
    LaunchedEffect(true) {
        viewModel.loadFriends()
        uiState = UiState.Success<FriendListModel>(listModelMock)
    }
    viewModel.resgisteredEventsListFlow.collectAsState().value.let { state ->
        uiState = state
    }
    CommonScreen(state = uiState) {
        Scaffold(
            modifier = Modifier
                .testTag("FriendsListScreen"),
        ) {
            FriendsList(
                listModelMock,
            )
        }
    }*/

    FriendsList(
        listModelMock,
    )
}

@Preview(showBackground = true)
@Composable
fun FriendsListPreview() {
    FriendsList(listModelMock)
}

@Composable
fun FriendsList(
    registeredEventsListModel: FriendListModel,
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item(registeredEventsListModel.headerText) {
            FriendsListHeader(registeredEventsListModel.headerText)
        }
        items(registeredEventsListModel.items) { item ->
            FriendsListItem(item)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FriendsListItemPreview() {
    FriendsListItem(itemMock01)
}

@Composable
fun FriendsListItem(
    item: FriendListItemModel
) {
    Card(
        elevation = 8.dp, modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .testTag("FriendsListItem")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Blue90)
        ) {
            Text(
                modifier = Modifier.padding(8.dp), text = item.name, fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = Navy
            )
        }
    }
}

@Composable
fun FriendsListHeader(
    title: String = "Título"
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = title, fontSize = 24.sp, textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold, color = Navy
        )
    }
}
