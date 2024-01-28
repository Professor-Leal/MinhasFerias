package br.com.rafaelleal.minhasferias.presentation_friends.ui.single

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.domain.models.Friend
import br.com.rafaelleal.minhasferias.presentation_common.screens.buttons.AnimatedSaveButton
import br.com.rafaelleal.minhasferias.presentation_common.screens.inputs.AddTextInput
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_friends.models.FriendListItemModel
import br.com.rafaelleal.minhasferias.presentation_friends.ui.list.FriendsListItem
import br.com.rafaelleal.minhasferias.presentation_friends.viewmodels.FriendsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddFriendScreen(
    viewModel: FriendsListViewModel,
    backToMain: () -> Unit = {}
) {
    var uiState: UiState<Unit> by remember {
        mutableStateOf(UiState.Loading)
    }

    viewModel.saveFriendFlow.collectAsState().value.let { state ->
        uiState = state
        when (uiState) {
            is UiState.Success -> {
                backToMain()
            }

            else -> {}
        }
    }

    val saveItem: (Friend) -> Unit = {
        viewModel.saveFriend(it)
    }

    FormAddFriendScreen() {
        saveItem(it)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormAddFriendScreen(
    saveItem: (input: Friend) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var documentNumber by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    fun updateButtonVisibility() {
        visible =
            name.length > 2 && surname.length > 2 && phoneNumber.length > 8 && documentNumber.length > 6
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue90)
    ) {
        AddFriendsListHeader()
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
            AnimatedSaveButton(visible) {
                coroutineScope.launch(Dispatchers.IO) {
                    saveItem(getItem(name, surname, phoneNumber, documentNumber))
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

@RequiresApi(Build.VERSION_CODES.O)
fun getItem(name: String, surname: String, phoneNumber: String, documentNumber: String): Friend {
    return Friend(
        name = name,
        surname = surname,
        phoneNumber = phoneNumber,
        documentNumber = documentNumber,
        0L
    )
}

@Composable
fun AddFriendsListHeader(
    title: String = "Add a New Friend"
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .testTag("AddFriendsListHeader")
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