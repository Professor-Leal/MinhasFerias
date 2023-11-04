package br.com.rafaelleal.minhasferias.presentation_registered_events.single

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_registered_events.R
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListItem
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.RegisteredEventsListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddRegisteredEventScreen(
    viewModel: RegisteredEventsListViewModel,
    backToMain: () -> Unit = {}
) {
    var uiState: UiState<Unit> by remember {
        mutableStateOf(UiState.Loading)
    }
    viewModel.saveRegisteredEventFlow.collectAsState().value.let { state ->
        uiState = state
        when (uiState) {
            is UiState.Success -> {
                    backToMain()
            }
            else -> {}
        }
    }

    val saveItem: (RegisteredEvent) -> Unit = {
        viewModel.saveRegisteredEvent(it)
    }


    FormAddRegisteredEventScreen() {
        saveItem(it)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddRegisteredEventScreenPreview() {
    FormAddRegisteredEventScreen()
}

@Composable
fun FormAddRegisteredEventScreen(
    saveItem: (input: RegisteredEvent) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    fun updateButtonVisibility() {
        visible = name.length > 0 && day.length > 0 && time.length > 0 && address.length > 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue90)
    ) {
        AddRegisteredEventsListHeader()
        AddTextInput("Nome do Evento", name) {
            name = it
            updateButtonVisibility()
        }
        AddTextInput("Dia do Evento", day) {
            day = it
            updateButtonVisibility()
        }
        AddTextInput("Hora do Evento", time) {
            time = it
            updateButtonVisibility()
        }
        AddTextInput("Endereço", address) {
            address = it
            updateButtonVisibility()
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SaveRegisteredEventButton(visible) {
                coroutineScope.launch(IO) {
                    saveItem(getItem(name, day, time, address))
                }
            }
        }
        RegisteredEventsListItem(
            RegisteredEventListItemModel(
                name,
                address,
                "$day - $time",
                0L
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormAddRegisteredEventScreenPreview() {
    FormAddRegisteredEventScreen()
}

@Composable
fun AddRegisteredEventsListHeader(
    title: String = "Add a New Event"
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .testTag("AddRegisteredEventsListHeader")
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

@Preview(showBackground = true)
@Composable
fun AddRegisteredEventsListHeaderPreview() {
    AddRegisteredEventsListHeader("Add a New Event")
}


@Composable
fun AddTextInput(label: String, text: String, onValueChange: (inputText: String) -> Unit = {}) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                label,
                style = TextStyle(
                    color = MaterialTheme.colors.primaryVariant,
                ),
                fontSize = 18.sp,

                )
        },
        textStyle = TextStyle(fontSize = 18.sp, fontFamily = FontFamily.Default),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = White,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
            .fillMaxWidth(1f)
            .testTag(label)
    )
}

@Preview(showBackground = true)
@Composable
fun AddTextInputPreview() {
    AddTextInput("Nome do Evento", "Evento Bacana") {

    }
}

@Composable
fun SaveRegisteredEventButton(visible: Boolean, onClick: () -> Unit) {

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Button(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("SaveRegisteredEventButton"),
            onClick = { onClick() }) {
            Text(stringResource(R.string.save))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaveRegisteredEventButtonPreview() {
    SaveRegisteredEventButton(true) { }
}

fun getItem(name: String, day: String, time: String, address: String): RegisteredEvent {
    return RegisteredEvent(
        name = name, address = address, time = time, day = day, 0L
    )
}