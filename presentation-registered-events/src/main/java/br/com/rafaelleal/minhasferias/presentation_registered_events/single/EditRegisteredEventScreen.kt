package br.com.rafaelleal.minhasferias.presentation_registered_events.single

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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Notifications
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_registered_events.R
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListItem
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.EditRegisteredEventViewModel
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.ButtonStyle
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditRegisteredEventScreen(
    registeredEventId: Long,
    viewModel: EditRegisteredEventViewModel,
    backToMain: () -> Unit = {}
) {
    var uiStateBackToMain: UiState<Boolean> by remember {
        mutableStateOf(UiState.Loading)
    }
    viewModel.updateRegisteredEventFlow.collectAsState().value.let { _state ->
        uiStateBackToMain = _state
        when (uiStateBackToMain) {
            is UiState.Success -> {
                backToMain()
            }
            else -> {}
        }
    }
    viewModel.deleteRegisteredEventFlow.collectAsState().value.let { _state ->
        uiStateBackToMain = _state
        when (uiStateBackToMain) {
            is UiState.Success -> {
                backToMain()
            }
            else -> {}
        }
    }

    var uiState: UiState<RegisteredEvent> by remember {
        mutableStateOf(UiState.Loading)
    }
    LaunchedEffect(true) {
        viewModel.loadRegisteredEvent(registeredEventId)
    }
    viewModel.resgisteredEventFlow.collectAsState().value.let { state ->
        uiState = state
    }

    val updateRegisteredEvent: (RegisteredEvent) -> Unit = {
        viewModel.updateRegisteredEvent(it)
    }
    val deleteRegisteredEvent: (Long) -> Unit = {
        viewModel.deleteRegisteredEvent(it)
    }

    CommonScreen(state = uiState) {
        FormEditRegisteredEventScreen(it, updateRegisteredEvent, deleteRegisteredEvent)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormEditRegisteredEventScreen(
    item: RegisteredEvent,
    updateRegisteredEvent: (input: RegisteredEvent) -> Unit = {},
    deleteRegisteredEvent: (registeredEventId: Long) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var itemId by remember { mutableStateOf(item.id) }
    var name by remember { mutableStateOf(item.name) }
    var day by remember { mutableStateOf(item.day) }
    var time by remember { mutableStateOf(item.time) }
    var address by remember { mutableStateOf(item.address) }
    var updateButtonVisible by remember { mutableStateOf(false) }

    fun updateButtonVisibility() {
        updateButtonVisible =
            name.length > 0 && day.length > 0 && time.length > 0 && address.length > 0
    }

    val closeDateSelection: UseCaseState.() -> Unit = { updateButtonVisibility() }
    val calendarState =
        rememberUseCaseState(visible = false, onCloseRequest = { closeDateSelection() })
    val clockState =
        rememberUseCaseState(visible = false, onCloseRequest = { closeDateSelection() })
    val deleteState =
        rememberUseCaseState(visible = false, onCloseRequest = { closeDateSelection() })

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
            time = formatTime(LocalTime.of(hours, minutes, 0))
        },
        config = ClockConfig(
            defaultTime = LocalTime.NOON,
            is24HourFormat = true
        )
    )
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
            deleteRegisteredEvent(itemId)
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
        EditRegisteredEventHeader()
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
            UpdateRegisteredEventButton(updateButtonVisible) {
                coroutineScope.launch(Dispatchers.IO) {
                    updateRegisteredEvent(getUpdateItem(itemId, name, day, time, address))
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
@Preview(showSystemUi = true)
@Composable
fun FormEditRegisteredEventScreenPreview() {
    FormEditRegisteredEventScreen(
        RegisteredEvent(
            name = "Nome do Evento",
            address = "Endereço do evento",
            time = "12:34",
            day = "01/02/2023",
            date = LocalDateTime.of(2023, 2, 1, 12, 34),
            id = 1L
        )
    )
}


@Composable
fun UpdateRegisteredEventButton(visible: Boolean, onClick: () -> Unit) {

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Button(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("UpdateRegisteredEventButton"),
            onClick = { onClick() }) {
            Text(stringResource(R.string.update))
        }
    }
}

@Composable
fun EditRegisteredEventHeader(
    title: String = "Edit the Event"
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .testTag("EditRegisteredEventHeader")
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

@RequiresApi(Build.VERSION_CODES.O)
fun getUpdateItem(
    id: Long,
    name: String,
    day: String,
    time: String,
    address: String
): RegisteredEvent {
    return RegisteredEvent(
        name = name, address = address, time = time, day = day, getFormattedDate(day, time), id
    )
}