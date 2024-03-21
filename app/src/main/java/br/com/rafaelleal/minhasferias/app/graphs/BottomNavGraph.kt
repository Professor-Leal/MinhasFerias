package br.com.rafaelleal.minhasferias.app.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.rafaelleal.minhasferias.presentation_common.sealed.FriendsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.RegisteredEventsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToAddNewEvent
import br.com.rafaelleal.minhasferias.presentation_friends.ui.list.FriendsListScreen
import br.com.rafaelleal.minhasferias.presentation_registered_events.ui.list.RegisteredEventsListScreen

const val GRAPH_ROUTE_BOTTOM = "graph_bottom"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = GRAPH_ROUTE_BOTTOM,
        startDestination = GRAPH_ROUTE_EVENTS
    ) {
            registeredEventsNavGraph(navController)
            friendsNavGraph(navController)
    }

}