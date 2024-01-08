package br.com.rafaelleal.minhasferias.app.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.rafaelleal.minhasferias.presentation_common.sealed.RegisteredEventsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToAddNewEvent
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToHome
import br.com.rafaelleal.minhasferias.presentation_registered_events.list.RegisteredEventsListScreen
import br.com.rafaelleal.minhasferias.presentation_registered_events.single.AddRegisteredEventScreen
import br.com.rafaelleal.minhasferias.presentation_registered_events.single.EditRegisteredEventScreen


const val GRAPH_ROUTE_EVENTS = "graph_events"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registeredEventsNavGraph(
    navController: NavHostController
){
    navigation(
        route = GRAPH_ROUTE_EVENTS,
        startDestination = RegisteredEventsNavRoutes.Events.route
    ){
        composable(route = RegisteredEventsNavRoutes.Events.route) {
            RegisteredEventsListScreen(
                hiltViewModel(),
                navController
            ) { navController.navigateToAddNewEvent() }
        }
        composable(
            route = RegisteredEventsNavRoutes.AddNewEvent.route,
        ) {
            AddRegisteredEventScreen(
                hiltViewModel()
            ) { navController.navigateToHome() }
        }
        composable(
            route = RegisteredEventsNavRoutes.Event.route,
            arguments = RegisteredEventsNavRoutes.Event.arguments
        ) {
            EditRegisteredEventScreen(
                RegisteredEventsNavRoutes.Event.fromEntry(it),
                hiltViewModel()
            ) { navController.navigateToHome() }
        }
    }
}