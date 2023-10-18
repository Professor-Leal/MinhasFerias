package br.com.rafaelleal.minhasferias.presentation_registered_events.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.screens.CommonScreen
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Black
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue10
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue20
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue20_60
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue20_70
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue20_80
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue20_90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue30
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue50
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue80
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel

@Composable
fun RegisteredEventsListScreen(
    viewModel: RegisteredEventsListViewModel,
) {
    viewModel.loadRegisteredEvents()
    viewModel.resgisteredEventsListFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            RegisteredEventsList(it)
        }
    }
}

internal val items: List<RegisteredEventListItemModel> = listOf(
    RegisteredEventListItemModel(
        name = "Evento 01 ",
        address = "Rua da Esquina",
        dayTime = "12/12/2023 - 12:00",
        id = 1
    ),
    RegisteredEventListItemModel(
        name = "Evento 02 ",
        address = "Do outro lado de lá",
        dayTime = "13/12/2023 - 14:00",
        id = 2
    ),
    RegisteredEventListItemModel(
        name = "Evento 03 ",
        address = "Logo Ali",
        dayTime = "15/12/2023 - 17:00",
        id = 3
    ),
)

@Composable
fun RegisteredEventsList(
    registeredEventsListModel: RegisteredEventsListModel
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item(registeredEventsListModel.headerText) {
            RegisteredEventsListHeader(registeredEventsListModel.headerText)
        }
        items(
            items
//            registeredEventsListModel.items
        ) { item ->
            RegisteredEventsListItem(item)
        }
    }
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