package br.com.rafaelleal.minhasferias.app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.rafaelleal.minhasferias.app.graphs.BottomBarScreen
import br.com.rafaelleal.minhasferias.app.graphs.BottomNavGraph
import br.com.rafaelleal.minhasferias.app.graphs.GRAPH_ROUTE_EVENTS
import br.com.rafaelleal.minhasferias.app.graphs.friendsNavGraph
import br.com.rafaelleal.minhasferias.app.graphs.registeredEventsNavGraph
import br.com.rafaelleal.minhasferias.app.ui.theme.Blue60
import br.com.rafaelleal.minhasferias.app.ui.theme.MinhasFériasTheme
import br.com.rafaelleal.minhasferias.presentation_common.sealed.FriendsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.RegisteredEventsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToAddNewEvent
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue20
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue70
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Orange70
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Orange80
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White
import br.com.rafaelleal.minhasferias.presentation_friends.ui.list.FriendsListScreen
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListScreen
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
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Events,
        BottomBarScreen.Friends
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Blue20
    ) {
        screens.forEach { _screen ->
            AddItem(screen = _screen, currentDestination = currentDestination, navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title, color = getSelectedIconColor(
            currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true
        )) },
        icon = {
            Icon(
                screen.icon,
                contentDescription = "Navigation Icon ${screen.title}",
                tint = getSelectedIconColor(currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    )
}

fun getSelectedIconColor(selected: Boolean): Color {
    return if(selected) { Blue70 } else { White}
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun AppNavHostPreview() {
    AppNavHost(rememberNavController())
}


