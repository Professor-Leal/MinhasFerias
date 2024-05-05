package br.com.rafaelleal.minhasferias.app.test

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
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

@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        MockDb.initializeDatabase()
    }

    @After
    fun tearDown() {
        MockDb.closeDatabase()
    }

    val timeOutToShowScreenView = 5000L


    fun waitInitialFabLoad() {
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
    }

    fun clickOnNodeWithText(text: String) {
        composeTestRule
            .onNodeWithText(text)
            .performClick()
    }

    fun clickOnNodeWithTag(tag: String) {
        composeTestRule
            .onNodeWithTag(tag)
            .performClick()
    }

    fun waitNodeWithTagLoad(tag: String) {
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag(tag)
                .fetchSemanticsNodes().size == 1
        }
    }

    fun nodeWithTextExists(text: String) {
        composeTestRule.onNodeWithText(text).assertExists()
    }

    fun nodeWithTextDoesNotExist(text: String) {
        composeTestRule.onNodeWithText(text).assertDoesNotExist()
    }

    fun writeOnNodeWithTag(tag: String, input: String) {
        composeTestRule.onNodeWithTag(tag).performTextInput(input)
    }

    fun clickOnNodeWithContentDescription(description: String) {
        composeTestRule.onNodeWithContentDescription(description).performClick()
    }

    fun waitAllNodeWithTagEqualsTo(tag: String, quantity: Int) {
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag(tag)
                .fetchSemanticsNodes().size == quantity
        }
    }


    // EventFriends SAcreen tests
    @Test
    fun shouldAddFriendsToEvent() {
        // Build database data:
        addTwoRegisteredEventsToDB()
        addTwoFriendsToDB()

        waitInitialFabLoad()

        clickOnNodeWithText("name 1")

        waitNodeWithTagLoad("EditRegisteredEventHeader")

        clickOnNodeWithTag("AddFriendsButton")

        waitNodeWithTagLoad("EventCard")

        nodeWithTextExists("John Doe")
        nodeWithTextExists("Jane Doe")

        waitAllNodeWithTagEqualsTo("EmptyCheckIcon", 2)
        waitAllNodeWithTagEqualsTo("CheckIcon", 0)

        clickOnNodeWithTag("FriendFromEvent-1")

        waitAllNodeWithTagEqualsTo("CheckIcon", 1)
        waitAllNodeWithTagEqualsTo("EmptyCheckIcon", 1)

    }

    @Test
    fun should_FilterFriendsInAddFriendsScreen() {
        // Build database data:
        addTwoRegisteredEventsToDB()
        addTwoFriendsToDB()
        addEventFriendToDb()

        waitInitialFabLoad()

        clickOnNodeWithText("name 1")

        waitNodeWithTagLoad("EditRegisteredEventHeader")

        clickOnNodeWithTag("AddFriendsButton")

        waitNodeWithTagLoad("EventCard")

        nodeWithTextExists("John Doe")
        nodeWithTextExists("Jane Doe")

        clickOnNodeWithTag("SearchTextField")
        writeOnNodeWithTag("SearchTextFieldInput", "John")

        clickOnNodeWithContentDescription("Search Icon")

        nodeWithTextExists("John Doe")
        nodeWithTextDoesNotExist("Jane Doe")

    }


    // RegisteredEvents Screen tests
    @Test
    fun should_FillForm_whenWritingOnAddEventScreen_andSave() {

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

    @Test
    fun should_navigateToEditScreen_whenItemIsClicked_thenDeleteItem() {
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

        // clica e edita o nome
        composeTestRule.onNodeWithTag("DeleteIcon").performClick()

        // Espera aparecer o diálogo de confirmação
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithText("Yes")
                .fetchSemanticsNodes().size == 1
        }

        // Clica no botão de sim
        composeTestRule.onNodeWithText("Yes").performClick()

        // volta para a tela inicial
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }

        // Verifica que o item 1 foi apagado
        composeTestRule.onNodeWithText("name 1").assertDoesNotExist()


    }

    // Friends Screen tests
    @Test
    fun shouldFillForm_whenWritingOnAddFriendScreen_andSave() {
        // Espera o FAB aparecer para ter certeza que a tela foi carregada
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }

        // clica no ícone da aba de amigos:
        composeTestRule
            .onNodeWithContentDescription("Navigation Icon Friends")
            .performClick()

        // Espera o FAB da tela de amigos aparecer para ter certeza que a tela foi carregada
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_friend")
                .fetchSemanticsNodes().size == 1
        }

        // Clica no botão de adicionar amigos
        composeTestRule
            .onNodeWithTag("fab_add_new_friend")
            .performClick()


        // Espera a nova tela carregar
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("Nome")
                .fetchSemanticsNodes().size == 1
        }

        // Preenche o formulário e verifica que o botão de salvar não aprarece até preencher
        composeTestRule.onNodeWithTag("AnimatedSaveButtonTag").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Nome").performTextInput("João")

        composeTestRule.onNodeWithTag("AnimatedSaveButtonTag").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Sobrenome").performTextInput("da Silva")

        composeTestRule.onNodeWithTag("AnimatedSaveButtonTag").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Telefone").performTextInput("(21) 99999-1234")

        composeTestRule.onNodeWithTag("AnimatedSaveButtonTag").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Documento").performTextInput("123.456.789-00")

        composeTestRule.onNodeWithTag("AnimatedSaveButtonTag").assertExists()

        // Verifica se o item mostra os campos preenchidos:
        composeTestRule.onAllNodesWithText("João da Silva").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("(21) 99999-1234").assertCountEquals(2)
        composeTestRule.onAllNodesWithText("123.456.789-00").assertCountEquals(2)

        // Clica no botão de salvar
        composeTestRule.onNodeWithTag("AnimatedSaveButtonTag").performClick()

        // Volta para a tela inicial e verifica se o item foi criado
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("fab_add_new_friend")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("João da Silva", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun should_navigateToFriendEditScreen_whenItemIsClicked_thenEditAndUpdate() {
        // Alimenta o banco de dados com itens da lista
        addTwoFriendsToDB()

        // Espera o FAB aparecer
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }

        // clica no ícone da aba de amigos:
        composeTestRule
            .onNodeWithContentDescription("Navigation Icon Friends")
            .performClick()

        // Espera o FAB da tela de amigos aparecer para ter certeza que a tela foi carregada
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_friend")
                .fetchSemanticsNodes().size == 1
        }

        // clica no item 01
        composeTestRule
            .onNodeWithText("John Doe")
            .performClick()

        // Espera a tela de edição aparacer
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("EditFriendHeader")
                .fetchSemanticsNodes().size == 1
        }

        // Verifica que o botão de atualizar ainda não está visível
        composeTestRule.onNodeWithTag("UpdateFriendButton").assertDoesNotExist()

        // clica e edita o nome
        composeTestRule.onNodeWithTag("Nome").performTextInput(
            " Pedro"
        )

        // Espera botão de atualizar ficar visível
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("UpdateFriendButton")
                .fetchSemanticsNodes().size == 1
        }

        // Clica no botão de atualizar
        composeTestRule.onNodeWithTag("UpdateFriendButton").performClick()

        // Espera voltar para a tela inicial e verifica se o item foi atualizado
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithText("John Pedro Doe")
                .fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun should_navigateToEditFriendScreen_whenItemIsClicked_thenDeleteItem() {
        // Alimenta o banco de dados com itens da lista
        addTwoFriendsToDB()
        // Espera o FAB aparecer
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }

        // clica no ícone da aba de amigos:
        composeTestRule
            .onNodeWithContentDescription("Navigation Icon Friends")
            .performClick()

        // Espera o FAB da tela de amigos aparecer para ter certeza que a tela foi carregada
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_friend")
                .fetchSemanticsNodes().size == 1
        }

        // clica no item 01
        composeTestRule
            .onNodeWithText("John Doe")
            .performClick()

        // Espera a tela de edição aparacer
        composeTestRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeTestRule.onAllNodesWithTag("EditFriendHeader")
                .fetchSemanticsNodes().size == 1
        }

        // clica e edita o nome
        composeTestRule.onNodeWithTag("DeleteIcon").performClick()

        // Espera aparecer o diálogo de confirmação
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithText("Yes")
                .fetchSemanticsNodes().size == 1
        }

        // Clica no botão de sim
        composeTestRule.onNodeWithText("Yes").performClick()

        // volta para a tela inicial
        composeTestRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_friend")
                .fetchSemanticsNodes().size == 1
        }

        // Verifica que o item 1 foi apagado
        composeTestRule.onNodeWithText("John Doe").assertDoesNotExist()


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Funções auxiliares:
    fun addTwoRegisteredEventsToDB() {
        MockDb.addTwoRegisteredEventsToDB()
    }

    fun addTwoFriendsToDB() {
        MockDb.addTwoFriendsToDB()
    }

    fun addEventFriendToDb() {
        MockDb.addEventFriendToDb()
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