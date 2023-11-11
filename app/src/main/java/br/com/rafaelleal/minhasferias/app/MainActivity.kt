package br.com.rafaelleal.minhasferias.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.rafaelleal.minhasferias.app.ui.theme.MinhasFériasTheme
import br.com.rafaelleal.minhasferias.presentation_common.sealed.NavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateSingleTopTo
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToAddNewEvent
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListScreen
import br.com.rafaelleal.minhasferias.presentation_registered_events.single.AddRegisteredEventScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasFériasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(navController: NavHostController) {
    AppNavHost(navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoutes.Events.route) {
        composable(route = NavRoutes.Events.route) {
            RegisteredEventsListScreen(
                hiltViewModel()
            ) { navController.navigateToAddNewEvent() }
        }
        composable(
            route = NavRoutes.AddNewEvent.route,
        ) {
            AddRegisteredEventScreen(
                hiltViewModel()
            ) { navController.navigateSingleTopTo(NavRoutes.Events.route) }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AppNavHostPreview() {
    AppNavHost(rememberNavController())
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App(rememberNavController())
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MinhasFériasTheme {
        Greeting("Android")
    }
}


