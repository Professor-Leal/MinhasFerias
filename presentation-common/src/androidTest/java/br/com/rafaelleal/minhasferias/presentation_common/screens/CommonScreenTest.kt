package br.com.rafaelleal.minhasferias.presentation_common.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.sealed.UiState
import org.junit.Rule
import org.junit.Test

class CommonScreenTest {
    @Composable
    fun HelloTest(content: String) {
        Text(modifier = Modifier.fillMaxWidth(), text = "Hello $content", fontSize = 24.sp)
    }

    val contentText = "TestSuccess"
    val errorMessage = "Error - Message"

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun show_Loading_WhenUiStateIsLoading() {

        rule.setContent {
            CommonScreen(state = UiState.Loading) {
                HelloTest(contentText)
            }
        }
        rule.onNodeWithTag("Loading").assertIsDisplayed()
        rule.onNodeWithTag("Snackbar").assertDoesNotExist()
        rule.onNodeWithText(errorMessage).assertDoesNotExist()
        rule.onNodeWithText("Hello $contentText").assertDoesNotExist()
    }

    @Test
    fun show_SnackBar_WhenUiStateIsError() {
        rule.setContent {
            CommonScreen(state = UiState.Error(errorMessage)) {
                HelloTest(contentText)
            }
        }
        rule.onNodeWithTag("Snackbar").assertIsDisplayed()
        rule.onNodeWithText(errorMessage).assertExists()
        rule.onNodeWithTag("Loading").assertDoesNotExist()
        rule.onNodeWithText("Hello $contentText").assertDoesNotExist()
    }

    @Test
    fun show_Content_WhenUiStateIsSuccess() {
        rule.setContent {
            CommonScreen(state = UiState.Success(contentText)) {
                HelloTest(it)
            }
        }
        rule.onNodeWithTag("Snackbar").assertDoesNotExist()
        rule.onNodeWithText(errorMessage).assertDoesNotExist()
        rule.onNodeWithTag("Loading").assertDoesNotExist()
        rule.onNodeWithText("Hello $contentText").assertExists()
    }

}