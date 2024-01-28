package br.com.rafaelleal.minhasferias.presentation_friends.ui.list

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToEditEvent
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToEditFriend
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Orange80
import br.com.rafaelleal.minhasferias.presentation_friends.R
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListModel
import br.com.rafaelleal.minhasferias.presentation_friends.viewmodels.FriendsListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


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
internal val listModelEmptyMock = FriendListModel("Título Preview", listOf())


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FriendsListScreen(
    viewModel: FriendsListViewModel,
    navController: NavHostController,
    onClickAddNewFriend: () -> Unit = {},
) {
    var uiState: UiState<FriendListModel> by remember {
        mutableStateOf(UiState.Loading)
    }
    LaunchedEffect(true) {
        viewModel.loadFriends()
    }
    viewModel.friendsListFlow.collectAsState().value.let { state ->
        uiState = state
    }
    CommonScreen(state = uiState) {
        FriendsScaffoldBody(it, navController, onClickAddNewFriend)
        FriendsBackHandler()
    }

}

@Composable
fun FriendsBackHandler() {
    val activity = (LocalContext.current as? Activity)
    var pressedTime by remember { mutableStateOf(0L) }

    BackHandler(enabled = true, onBack = {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            activity?.finish()
        } else {
            Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    })
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FriendsScaffoldBody(
    friendListModel: FriendListModel,
    navController: NavHostController,
    onClickAddNewFriend: () -> Unit = {}
) {
    var shown by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = Modifier
        .testTag("FriendsListScreen"),
        floatingActionButton = {
            AnimatedVisibility(
                visible = shown,
                enter = slideIn(tween(600, easing = LinearOutSlowInEasing)) { fullSize ->
                    IntOffset(fullSize.width / 4, fullSize.width / 4)
                }
                        + fadeIn(initialAlpha = 0.3f),
                exit = slideOut(tween(600, easing = FastOutSlowInEasing)) { fullSize ->
                    IntOffset(fullSize.width / 4, fullSize.width / 4)
                }
                        + fadeOut(),
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .testTag("fab_add_new_friend"),
                    onClick = {
                        coroutineScope.launch {
                            shown = false
                            delay(300L)
                            onClickAddNewFriend()
                        }
                    }, containerColor = Blue90, contentColor = Navy, shape = CircleShape
                ) {
                    Icon(Icons.Filled.Add, "fab_add_new_friend")
                }
            }
        }
    ) {
        FriendsList(
            friendListModel,
            navController
        )
        LaunchedEffect(true) {
            delay(100L)
            shown = true
        }
    }
}

@Preview
@Composable
fun FriendsScaffoldBodyPreview() {
    FriendsScaffoldBody(listModelMock, navController = rememberNavController())
}

@Preview
@Composable
fun FriendsScaffoldBodyEmptyListPreview() {
    FriendsScaffoldBody(listModelEmptyMock, navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun FriendsListPreview() {
    FriendsList(listModelMock, rememberNavController())
}

@Composable
fun FriendsList(
    friendListModel: FriendListModel,
    navController: NavHostController
) {
    if (friendListModel.items.isEmpty()) {
        AddFriendsBanner()
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item(friendListModel.headerText) {
                FriendsListHeader(friendListModel.headerText)
            }
            items(friendListModel.items) { item ->
                FriendsListItem(item, navController)
            }
        }
    }
}


@Composable
fun AddFriendsBanner() {
    Box(
        modifier = Modifier
            .background(Orange80)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.add_friends_on_fab_click).uppercase(Locale.ROOT),
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Navy
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsListItemPreview() {
    FriendsListItem(itemMock01)
}

@Composable
fun FriendsListItem(
    item: FriendListItemModel,
    navController: NavHostController = rememberNavController()
) {
    Card(
        elevation = 8.dp, modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .testTag("FriendsListItem")
            .clickable {
                navController.navigateToEditFriend(item.id)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Blue90)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Blue90)
            ) {
                Text(
                    modifier = Modifier,
                    text = "${item.name} ${item.surname}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Blue90)
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "phone icon",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(24.dp),
                    tint = MaterialTheme.colors.primaryVariant
                )
                Text(
                    modifier = Modifier,
                    text = "${item.phoneNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Blue90)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "document icon",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(24.dp),
                    tint = MaterialTheme.colors.primaryVariant
                )
                Text(
                    modifier = Modifier,
                    text = "${item.documentNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
            }
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


