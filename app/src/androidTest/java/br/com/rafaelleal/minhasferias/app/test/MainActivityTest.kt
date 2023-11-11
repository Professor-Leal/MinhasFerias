package br.com.rafaelleal.minhasferias.app.test

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

    val timeOutShowFab = 5000L

    @Test
    fun onResume_shouldShowBannerAndFab() {
        composeTestRule.waitUntil(
            timeoutMillis = timeOutShowFab
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
            timeoutMillis = timeOutShowFab
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
            timeoutMillis = timeOutShowFab
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule
            .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
            .performClick()
        composeTestRule.waitUntil(
            timeoutMillis = timeOutShowFab
        ) {
            composeTestRule.onAllNodesWithTag("AddRegisteredEventsListHeader")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag("AddRegisteredEventsListHeader", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun shouldFillForm_whenWritingOnAddEventScreenAndSave() {

        composeTestRule.waitUntil(
            timeoutMillis = timeOutShowFab
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
//        Thread.sleep(2000)
        composeTestRule
            .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitUntil(
            timeoutMillis = timeOutShowFab
        ) {
            composeTestRule.onAllNodesWithTag("Nome do Evento")
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Nome do Evento").performTextInput("Novo Evento Teste")
        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Dia do Evento").performTextInput("01/01/2023")
        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Hora do Evento").performTextInput("20:00")
        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Endereço").performTextInput("Rua de cima")

        composeTestRule.waitUntil(
            timeoutMillis = timeOutShowFab
        ) {
            composeTestRule.onAllNodesWithTag("SaveRegisteredEventButton")
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule
            .onAllNodesWithText("Novo Evento Teste").assertCountEquals(2)
        composeTestRule
            .onAllNodesWithText("01/01/2023").assertCountEquals(1)
        composeTestRule
            .onAllNodesWithText("20:00").assertCountEquals(1)
        composeTestRule
            .onAllNodesWithText("01/01/2023 - 20:00").assertCountEquals(1)
        composeTestRule
            .onAllNodesWithText("Rua de cima").assertCountEquals(2)

//        Thread.sleep(2000)

        composeTestRule.onNodeWithTag("SaveRegisteredEventButton").performClick()

        composeTestRule.waitUntil(
            timeoutMillis = timeOutShowFab
        ) {
            composeTestRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("Novo Evento Teste", useUnmergedTree = true)
            .assertIsDisplayed()

//        Thread.sleep(3000)

    }

    fun addTwoRegisteredEventsToDB() {
        MockDb.addTwoRegisteredEventsToDB()
    }

}