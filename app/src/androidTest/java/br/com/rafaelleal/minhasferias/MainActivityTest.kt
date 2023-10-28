package br.com.rafaelleal.minhasferias

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    @Test
    fun onResume_shouldShowListItemsAndFab() {
        composeTestRule
            .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("name 1", useUnmergedTree = true)
            .assertExists()
        composeTestRule.onNodeWithText("name 2", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun clickOnFab_shouldNavigateToAddNewRegisteredEvent() {
        composeTestRule
            .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("AddRegisteredEventsListHeader", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun shouldFillForm_whenWritingOnAddEventScreen(){
        composeTestRule
            .onNodeWithContentDescription("fab_add_new_event", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("Nome do Evento").performTextInput("Novo Evento Teste")
        composeTestRule.onNodeWithTag("Dia do Evento").performTextInput("01/01/2023")
        composeTestRule.onNodeWithTag("Hora do Evento").performTextInput("20:00")
        composeTestRule.onNodeWithTag("Endereço").performTextInput("Rua de cima")

    }

}