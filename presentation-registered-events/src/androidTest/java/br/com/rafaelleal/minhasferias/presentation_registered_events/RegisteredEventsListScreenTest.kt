package br.com.rafaelleal.minhasferias.presentation_registered_events

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.AddEventsBanner
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsList
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListHeader
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListItem
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.ScaffoldBody
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.listModelMock
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventListItemModel
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.models.RegisteredEventsListModel
import org.junit.Rule
import org.junit.Test
import java.util.Locale

class RegisteredEventsListScreenTest {

    val title = "Título da lista"
    val item01 = RegisteredEventListItemModel(
        name = "Evento 01",
        address = "Rua de Baixo",
        dayTime = "01/01/2023 - 12:00",
        id = 1L
    )
    val item02 = RegisteredEventListItemModel(
        name = "Evento 02",
        address = "Rua de Cima",
        dayTime = "01/01/2023 - 13:00",
        id = 2L
    )

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun show_fab_onResume(){
        rule.setContent {
            ScaffoldBody(registeredEventListModel = listModelMock)
        }
        rule.onNodeWithContentDescription("fab_add_new_event.").assertExists()
    }

    @Test
    fun show_RegisteredEventsListHeader() {
        rule.setContent {
            RegisteredEventsListHeader(title)
        }
        rule.onNodeWithText(title).assertExists()
    }

    @Test
    fun show_RegisteredEventsListItem() {
        rule.setContent {
            RegisteredEventsListItem(item01)
        }
        rule.onNodeWithText("Evento 01").assertExists()
        rule.onNodeWithText("Rua de Baixo").assertExists()
        rule.onNodeWithText("01/01/2023 - 12:00").assertExists()
    }

    @Test
    fun show_RegisteredEventsList() {
        rule.setContent {
            RegisteredEventsList(
                RegisteredEventsListModel(
                    headerText = "Título da lista",
                    items = listOf(item01, item02)
                )
            )
        }
        rule.onNodeWithText("Título da lista").assertExists()
        rule.onNodeWithText("Evento 01").assertExists()
        rule.onNodeWithText("Rua de Baixo").assertExists()
        rule.onNodeWithText("01/01/2023 - 12:00").assertExists()
        rule.onNodeWithText("Evento 02").assertExists()
        rule.onNodeWithText("Rua de Cima").assertExists()
        rule.onNodeWithText("01/01/2023 - 13:00").assertExists()
    }

    @Test
    fun show_AddEventsBanner() {
        rule.setContent {
            AddEventsBanner()
        }
        rule.onNodeWithText("Add events clicking on (+)".uppercase(Locale.ROOT)).assertExists()
    }

    @Test
    fun show_AddEventsBanner_whenListIsEmpty() {
        rule.setContent {
            RegisteredEventsList(
                RegisteredEventsListModel(
                    headerText = "Título da lista",
                    items = listOf()
                )
            )
        }
        rule.onNodeWithText("Add events clicking on (+)".uppercase(Locale.ROOT)).assertExists()
    }


}