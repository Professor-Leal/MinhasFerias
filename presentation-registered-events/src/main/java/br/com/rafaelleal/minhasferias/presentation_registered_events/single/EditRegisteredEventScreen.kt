package br.com.rafaelleal.minhasferias.presentation_registered_events.single

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.rafaelleal.minhasferias.domain.models.RegisteredEvent
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListItem
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.EditRegisteredEventViewModel
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
import java.time.LocalDate
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditRegisteredEventScreen(
    registeredEventId: Long,
    viewModel: EditRegisteredEventViewModel = hiltViewModel(),
    backToMain: () -> Unit = {}
) {

    var uiState: UiState<RegisteredEvent> by remember {
        mutableStateOf(UiState.Loading)
    }
    LaunchedEffect(true) {
        viewModel.getRegisteredEvent(registeredEventId)
    }

    viewModel.resgisteredEventFlow.collectAsState().value.let { state ->
        uiState = state
    }
    CommonScreen(state = uiState) {
        FormEditRegisteredEventScreen( it )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormEditRegisteredEventScreen(
    item: RegisteredEvent,
    saveItem: (input: RegisteredEvent) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf(item.name) }
    var day by remember { mutableStateOf(item.day) }
    var time by remember { mutableStateOf(item.time) }
    var address by remember { mutableStateOf(item.address) }
    var visible by remember { mutableStateOf(false) }

    fun updateButtonVisibility() {
        visible = name.length > 0 && day.length > 0 && time.length > 0 && address.length > 0
    }

    val closeDateSelection: UseCaseState.() -> Unit = { updateButtonVisibility() }
    val calendarState =
        rememberUseCaseState(visible = false, onCloseRequest = { closeDateSelection() })
    val clockState =
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue90)
    ) {
        EditRegisteredEventHeader()
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
                coroutineScope.launch(Dispatchers.IO) {
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

@Composable
fun EditRegisteredEventHeader(
    title: String = "Edit The Event"
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

@Preview(showBackground = true)
@Composable
fun EditRegisteredEventHeaderPreview() {
    EditRegisteredEventHeader()
}

fun onRegisteredEventCollectSuccess(data: RegisteredEvent) {

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditRegisteredEventScreenPreview() {
    EditRegisteredEventScreen(
        1L
    )
}