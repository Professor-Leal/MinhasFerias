package br.com.rafaelleal.minhasferias.presentation_registered_events.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Black
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue10
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Orange30
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_registered_events.R
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.RegisteredEventsListViewModel
import java.util.Locale


internal val itemMock01 = RegisteredEventListItemModel(
    name = "Evento 01 ",
    address = "Rua da Esquina",
    dayTime = "12/12/2023 - 12:00",
    id = 1
)
internal val itemMock02 = RegisteredEventListItemModel(
    name = "Evento 02 ",
    address = "Do outro lado de lá",
    dayTime = "13/12/2023 - 14:00",
    id = 2
)
internal val itemMock03 = RegisteredEventListItemModel(
    name = "Evento 03 ",
    address = "Logo Ali",
    dayTime = "15/12/2023 - 17:00",
    id = 3
)
internal val itemsMock: List<RegisteredEventListItemModel> = listOf(
    itemMock01, itemMock02, itemMock03
)

internal val listModelMock = RegisteredEventsListModel("Título Preview", itemsMock)
internal val listModelEmptyMock = RegisteredEventsListModel("Título Preview", listOf())


@Composable
fun RegisteredEventsListScreen(
    viewModel: RegisteredEventsListViewModel,
    onClickAddNewRegisteredEvent: () -> Unit = {}
) {
    viewModel.loadRegisteredEvents()
    viewModel.resgisteredEventsListFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            ScaffoldBody(it, onClickAddNewRegisteredEvent)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldBody(
    registeredEventListModel: RegisteredEventsListModel,
    onClickAddNewRegisteredEvent: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .testTag("fab_add_new_event"),
                onClick = onClickAddNewRegisteredEvent,
                containerColor = Blue90,
                contentColor = Navy,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "fab_add_new_event")
            }
        }
    ) {
        RegisteredEventsList(registeredEventListModel)
    }

}

@Preview
@Composable
fun ScaffoldBodyPreview() {
    ScaffoldBody(listModelMock)
}

@Preview
@Composable
fun ScaffoldBodyEmptyListPreview() {
    ScaffoldBody(listModelEmptyMock)
}

@Composable
fun RegisteredEventsList(
    registeredEventsListModel: RegisteredEventsListModel
) {
    if (registeredEventsListModel.items.isEmpty()) {
        AddEventsBanner()
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item(registeredEventsListModel.headerText) {

                RegisteredEventsListHeader(registeredEventsListModel.headerText)
            }

            items(
//                items
                registeredEventsListModel.items
            ) { item ->
                RegisteredEventsListItem(item)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisteredEventsListPreview() {
    RegisteredEventsList(listModelMock)
}

@Composable
fun RegisteredEventsListHeader(
    title: String = "Título"
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
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
fun RegisteredEventsListHeaderPreview() {
    RegisteredEventsListHeader("Título Preview")
}

@Composable
fun RegisteredEventsListItem(
    item: RegisteredEventListItemModel
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Blue90)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = item.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = item.address, fontSize = 14.sp,
                color = Black
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp),
                text = item.dayTime,
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                color = Blue10
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisteredEventsListItemPreview() {
    RegisteredEventsListItem(itemMock01)
}

@Composable
fun AddEventsBanner() {
    Box(
        modifier = Modifier
            .background(Orange30)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.add_events_on_fab_click).uppercase(Locale.ROOT),
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddEventsBannerPreview() {
    AddEventsBanner()
}


