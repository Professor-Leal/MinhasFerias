package br.com.rafaelleal.minhasferias.presentation_registered_events.single

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Orange30
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_registered_events.viewmodels.RegisteredEventsListViewModel

@Composable
fun AddRegisteredEventScreen(
    viewModel: RegisteredEventsListViewModel
) {
    FormAddRegisteredEventScreen()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddRegisteredEventScreenPreview() {
    FormAddRegisteredEventScreen()
}

@Composable
fun FormAddRegisteredEventScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue90)
    ) {
        item() {
            AddRegisteredEventsListHeader()
        }

        items(
            listOf(
                "Nome do Evento", "Dia do Evento", "Hora do Evento", "Endereço"
            )
        ) { item ->
            AddTextInput(item)
        }
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
fun AddTextInput(label: String) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = {
            Text(
                label,
                style = TextStyle(
                    color = MaterialTheme.colors.primaryVariant,
                ),
                fontSize = 18.sp,

                )
        },
        placeholder = {
            Text(
                text = label,
                style = TextStyle(
                    color = MaterialTheme.colors.primaryVariant,
                    textAlign = TextAlign.Center
                ),
                fontSize = 18.sp,
            )
        },
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
    )
}

@Preview(showBackground = true)
@Composable
fun AddTextInputPreview() {
    AddTextInput("Nome do Evento")
}