package br.com.rafaelleal.minhasferias.presentation_friends.ui.single

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.screens.inputs.AddTextInput
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue70
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_friends.R
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import br.com.rafaelleal.minhasferias.presentation_friends.ui.list.FriendsListItem
import br.com.rafaelleal.minhasferias.presentation_friends.viewmodels.EditFriendViewModel
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.ButtonStyle
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditFriendScreen(
    friendId: Long,
    viewModel: EditFriendViewModel,
    backToMain: () -> Unit = {}
) {
    var uiStateBackToMain: UiState<Boolean> by remember {
        mutableStateOf(UiState.Loading)
    }
    viewModel.updateFriendFlow.collectAsState().value.let { _state ->
        uiStateBackToMain = _state
        when (uiStateBackToMain) {
            is UiState.Success -> {
                backToMain()
            }

            else -> {}
        }
    }
    viewModel.deleteFriendFlow.collectAsState().value.let { _state ->
        uiStateBackToMain = _state
        when (uiStateBackToMain) {
            is UiState.Success -> {
                backToMain()
            }

            else -> {}
        }
    }

    var uiState: UiState<Friend> by remember {
        mutableStateOf(UiState.Loading)
    }
    LaunchedEffect(true) {
        viewModel.loadFriend(friendId)
    }
    viewModel.friendFlow.collectAsState().value.let { state ->
        uiState = state
    }

    val updateFriend: (Friend) -> Unit = {
        viewModel.updateFriend(it)
    }
    val deleteFriend: (Long) -> Unit = {
        viewModel.deleteFriend(it)
    }

    CommonScreen(state = uiState) {
        FormEditFriendScreen(it, updateFriend, deleteFriend)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormEditFriendScreen(
    item: Friend,
    updateFriend: (input: Friend) -> Unit = {},
    deleteFriend: (registeredEventId: Long) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var itemId by remember { mutableStateOf(item.id) }
    var name by remember { mutableStateOf(item.name) }
    var surname by remember { mutableStateOf(item.surname) }
    var phoneNumber by remember { mutableStateOf(item.phoneNumber) }
    var documentNumber by remember { mutableStateOf(item.documentNumber) }
    var updateButtonVisible by remember { mutableStateOf(false) }

    fun updateButtonVisibility() {
        updateButtonVisible =
            name.length > 2 && surname.length > 2 && phoneNumber.length > 5 && documentNumber.length > 5
    }

    val closeDialogSelection: UseCaseState.() -> Unit = { updateButtonVisibility() }

    val deleteState =
        rememberUseCaseState(visible = false, onCloseRequest = { closeDialogSelection() })

    CoreDialog(
        state = deleteState,
        selection = CoreSelection(
            withButtonView = true,
            negativeButton = SelectionButton(
                stringResource(R.string.no),
                type = ButtonStyle.OUTLINED
            ),
            positiveButton = SelectionButton(
                stringResource(R.string.yes),
                IconSource(Icons.Rounded.Delete),
                ButtonStyle.OUTLINED
            ),
        ) {
            deleteFriend(itemId)
        },
        onPositiveValid = true,
        body = {
            Text(
                text = stringResource(R.string.sure_delete),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Navy
            )
        },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue90)
    ) {
        EditFriendHeader()
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "delete button",
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .testTag("DeleteIcon")
                    .clickable { deleteState.show() }
            )
        }
        AddTextInput("Nome", name) {
            name = it
            updateButtonVisibility()
        }
        AddTextInput("Sobrenome", surname) {
            surname = it
            updateButtonVisibility()
        }
        AddTextInput("Telefone", phoneNumber) {
            phoneNumber = it
            updateButtonVisibility()
        }
        AddTextInput("Documento", documentNumber) {
            documentNumber = it
            updateButtonVisibility()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            UpdateFriendButton(updateButtonVisible) {
                coroutineScope.launch(Dispatchers.IO) {
                    updateFriend(getUpdateItem(itemId, name, surname, phoneNumber, documentNumber))
                }
            }
        }
        FriendsListItem(
            FriendListItemModel(
                name,
                surname,
                phoneNumber,
                documentNumber,
                0L
            )
        )
    }
}

@Composable
fun UpdateFriendButton(visible: Boolean, onClick: () -> Unit) {

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Button(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("UpdateFriendButton"),
            colors = ButtonDefaults.buttonColors(backgroundColor = Navy, contentColor = White, disabledBackgroundColor = Blue70 ),
            onClick = { onClick() }) {
            Text(stringResource(R.string.update))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun getUpdateItem(
    id: Long,
    name: String, surname: String, phoneNumber: String, documentNumber: String
): Friend {
    return Friend(
        name = name,
        surname = surname,
        phoneNumber = phoneNumber,
        documentNumber = documentNumber,
        id
    )
}


@Composable
fun EditFriendHeader(
    title: String = "Edit the Friend"
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .testTag("EditFriendHeader")
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Navy
        )
    }
}
