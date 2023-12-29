package br.com.rafaelleal.minhasferias.app.test

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.rafaelleal.minhasferias.app.MainActivity
import br.com.rafaelleal.minhasferias.app.mocks.MockDb
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @After
    fun tearDown() {
        MockDb.clearAll()
    }

    val timeOutToShowScreenView = 5000L

    /*  @Test
      fun onResume_shouldShowBannerAndFab() {
          composeTestRule.waitUntil(
              timeoutMillis = timeOutToShowScreenView
          ) {
              composeTestRule.onAllNodesWithTag("fab_add_new_event")
                  .fetchSemanticsNodes().size == 1
          }
          composeTestRule
              .onNodeWithTag("fab_add_new_event", useUnmergedTree = false)
              .assertIsDisplayed()
          composeTestRule.onNodeWithText("Add events clicking on (+)".uppercase(Locale.ROOT))
              .assertExists()
      }

      @Test
      fun onResume_shouldShowListItemsAndFab() {
          addTwoRegisteredEventsToDB()
          composeTestRule.waitUntil(
              timeoutMillis = timeOutToShowScreenView
          ) {
              composeTestRule.onAllNodesWithTag("fab_add_new_event")
                  .fetchSemanticsNodes().size == 1
          }
          composeTestRule
              .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
              .assertIsDisplayed()

          composeTestRule.onNodeWithText("name 1", useUnmergedTree = true)
              .assertIsDisplayed()
          composeTestRule.onNodeWithText("name 2", useUnmergedTree = true)
              .assertIsDisplayed()
      }

      @Test
      fun clickOnFab_shouldNavigateToAddNewRegisteredEvent() {
          composeTestRule.waitUntil(
              timeoutMillis = timeOutToShowScreenView
          ) {
              composeTestRule.onAllNodesWithTag("fab_add_new_event")
                  .fetchSemanticsNodes().size == 1
          }
          composeTestRule
              .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
              .performClick()
          composeTestRule.waitUntil(
              timeoutMillis = timeOutToShowScreenView
          ) {
              composeTestRule.onAllNodesWithTag("AddRegisteredEventsListHeader")
                  .fetchSemanticsNodes().size == 1
          }
          composeTestRule.onNodeWithTag("AddRegisteredEventsListHeader", useUnmergedTree = true)
              .assertExists()
      }*/

    @Test
    fun shouldFillForm_whenWritingOnAddEventScreen_andSave() {

        // Variáveis auxiliares
        val monthInt = LocalDate.now().monthValue
        val yearInt = LocalDate.now().year
        val selectedDateString = formatDate(LocalDate.of(yearInt, monthInt, 17))
        val selectedTimeString = formatTime(LocalTime.of(4, 52))

        // Espera o FAB aparecer e clica nele navegando para a tela de criar
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule
            .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("Nome do Evento")
                .fetchSemanticsNodes().size == 1
        }

        // Preenche o formulário e verifica que o botão de salvar não aprarece até preencher
        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Nome do Evento").performTextInput(
            "Novo Evento Teste"
        )

        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Endereço").performTextInput("Rua de cima")

        // Clicar no dia do evento para abrir o diálogo
        composeTestRule.onNodeWithTag("Dia do Evento").performClick()
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithText("Cancel")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("17").performClick()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithText(selectedDateString)
                .fetchSemanticsNodes().size == 1
        }

        // Clicar na hora do evento para abrir o diálogo
        composeTestRule.onNodeWithTag("Hora do Evento").performClick()
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithText("Cancel")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithText(selectedTimeString)
                .fetchSemanticsNodes().size == 1
        }

        // Verifica se todos os campos estão preenchidos e clica em salvar
        composeTestRule.onAllNodesWithText("Novo Evento Teste").assertCountEquals(2)
        composeTestRule.onAllNodesWithText("Rua de cima").assertCountEquals(2)
        composeTestRule.onAllNodesWithText(selectedDateString).assertCountEquals(1)
        composeTestRule.onAllNodesWithText(selectedTimeString).assertCountEquals(1)
        composeTestRule.onAllNodesWithText("$selectedDateString - $selectedTimeString")
            .assertCountEquals(1)
        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").performClick()

        // Volta para a tela inicial e verifica se o item foi criado
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("Novo Evento Teste", useUnmergedTree = true)
            .assertIsDisplayed()

    }

    @Test
    fun should_navigateToEditScreen_whenItemIsClicked_thenEditAndUpdate() {
        // Alimenta o banco de dados com itens da lista
        addTwoRegisteredEventsToDB()

        // Espera o FAB aparecer
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }

        // clica no item 01
        composeTestRule
            .onNodeWithText("name 1")
            .performClick()

        // Espera a tela de edição aparacer
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("EditRegisteredEventHeader")
                .fetchSemanticsNodes().size == 1
        }

        // Verifica que o botão de atualizar ainda não está visível
        composeTestRule.onNodeWithTag("UpdateRegisteredEventButton").assertDoesNotExist()

        // clica e edita o nome
        composeTestRule.onNodeWithTag("Nome do Evento").performTextInput(
            " - updated"
        )

        // Clica no botão de atualizar
        composeTestRule.onNodeWithTag("UpdateRegisteredEventButton").performClick()

        // Espera voltar para a tela inicial e verifica se o item foi atualizado

        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithText("name 1 - updated")
                .fetchSemanticsNodes().size == 1
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Funções auxiliares:
    fun addTwoRegisteredEventsToDB() {
        MockDb.addTwoRegisteredEventsToDB()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date: LocalDate): String {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalTime): String {
        return DateTimeFormatter.ofPattern("hh:mm").format(time)
    }

}