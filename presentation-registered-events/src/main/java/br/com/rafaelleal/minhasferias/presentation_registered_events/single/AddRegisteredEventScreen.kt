package br.com.rafaelleal.minhasferias.presentation_registered_events.single

import android.os.Build
import android.util.Log
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_registered_events.R
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListItem
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.RegisteredEventsListViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Reference 01: https://www.youtube.com/watch?v=uAw87DdUnxg&t=226s
// Implement Material 3 Date/Time Pickers with Jetpack Compose! - Android Studio Tutorial -> Stevdza-San

// Reference 02: https://www.youtube.com/watch?v=6w4l-3jC21E
// Text Fields - Jetpack Compose  -> Stevdza-San


@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddRegisteredEventScreenPreview() {
    FormAddRegisteredEventScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): String {
    return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(time: LocalTime): String {
    return DateTimeFormatter.ofPattern("hh:mm").format(time)
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormAddRegisteredEventScreen(
    saveItem: (input: RegisteredEvent) -> Unit = {}
) {


    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var day by remember { mutableStateOf(formatDate(LocalDate.now())) }
    var time by remember { mutableStateOf(formatTime(LocalTime.NOON)) }
    var address by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    fun updateButtonVisibility() {
        visible = name.length > 0 && day.length > 0 && time.length > 0 && address.length > 0
    }

    val closeDateSelection: UseCaseState.() -> Unit = { updateButtonVisibility() }
    val calendarState =  rememberUseCaseState(visible = false, onCloseRequest = { closeDateSelection() })
    val clockState =  rememberUseCaseState(visible = false, onCloseRequest = { closeDateSelection() })


    CalendarDialog(
        calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { newDate ->
            day = formatDate(newDate)
        }
    )
    ClockDialog(
        clockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            time = formatTime(LocalTime.of(hours, minutes, 0) )
        },
        config = ClockConfig(
            defaultTime = LocalTime.NOON,
            is24HourFormat = true
        )
    )




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
        AddTextInput("Endereço", address) {
            address = it
            updateButtonVisibility()
        }

        SetDateTextInput("Dia do Evento", day) {
            updateButtonVisibility()
            calendarState.show()
        }
        SetTimeTextInput("Hora do Evento", time) {
            updateButtonVisibility()
            clockState.show()
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

@RequiresApi(Build.VERSION_CODES.O)
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
                fontSize = 18.sp
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

@RequiresApi(Build.VERSION_CODES.O)
fun getItem(name: String, day: String, time: String, address: String): RegisteredEvent {
    return RegisteredEvent(
        name = name, address = address, time = time, day = day, getFormattedDate(day, time), 0L
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedDate(day: String, time: String): LocalDateTime {
    val dateString = "$day $time"

    // Formato da string
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    // Fazendo o parsing da string para LocalDateTime
    val dateTime = LocalDateTime.parse(dateString, formatter)
    return dateTime
}


@Composable
fun SetDateTextInput(
    label: String,
    text: String,
    onClick: (outputString: String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    OutlinedTextField(
        value = text,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = MaterialTheme.colors.primaryVariant,
                ),
                fontSize = 18.sp,
                modifier = Modifier
                    .testTag("\${label}_Text")
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "dateRange",
                modifier = Modifier
                    .size(24.dp),
                tint = MaterialTheme.colors.primaryVariant
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
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    onClick("Clicked")
                } else {
                    // not focused
                }
            }
            .testTag(label)
    )


}

@Composable
fun SetTimeTextInput(
    label: String,
    text: String,
    onClick: (outputString: String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    OutlinedTextField(
        value = text,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = MaterialTheme.colors.primaryVariant,
                ),
                fontSize = 18.sp,
                modifier = Modifier
                    .testTag("\${label}_Text")
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_time_filled),
                contentDescription = "dateRange",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colors.primaryVariant
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
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    onClick("Clicked")
                } else {
                    // not focused
                }
            }
    )

}

@Preview(showBackground = true)
@Composable
fun SetDateTextInputPreview() {
    SetDateTextInput(
        "Data", "01/01/2023"
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun SetTimeTextInputPreview() {
    SetTimeTextInput(
        "Time", "12:15"
    ) {
    }
}