package br.com.rafaelleal.minhasferias

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.rafaelleal.minhasferias.presentation_common.sealed.NavRoutes
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.ScaffoldBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    val rule = createComposeRule()


    @Test
    fun show_fab_onResume(){
        rule.setContent {
            App()
        }
        rule.onNodeWithContentDescription("fab_add_new_event.").assertExists()
    }


//    lateinit var navController: TestNavHostController

//    @Before
//    fun setupRallyNavHost() {
//        composeTestRule.setContent {
//            // Creates a TestNavHostController
//            navController = TestNavHostController(LocalContext.current)
//            // Sets a ComposeNavigator to the navController so it can navigate through composables
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            AppNavHost(navController = navController)
//        }
//    }

//    // Falha por causa do hilt, por enquanto
//    @Test
//    fun navigateToAddNewEventScreen(){
//        composeTestRule.onNodeWithContentDescription("fab_add_new_event.")
//            .performClick()
//
//        val route = navController.currentBackStackEntry?.destination?.route
//        assertEquals(route, NavRoutes.AddNewEvent.route)
//    }



}