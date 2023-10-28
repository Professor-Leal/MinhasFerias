package br.com.rafaelleal.minhasferias.presentation_common.sealed

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument


private const val ROUTE_EVENTS = "events"
private const val ROUTE_ADD_EVENT = "add-new-event"
private const val ROUTE_EVENT = "event/%s"
private const val ARG_EVENT_ID = "eventId"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Events : NavRoutes(ROUTE_EVENTS)

    object AddNewEvent : NavRoutes(ROUTE_ADD_EVENT)

    object Event : NavRoutes(
        route = String.format(ROUTE_EVENT, "{$ARG_EVENT_ID}"),
        arguments = listOf(navArgument(ARG_EVENT_ID) {
            type = NavType.LongType
        })
    ) {
        fun routeForEvent(eventId: Long) = String.format(ROUTE_EVENT, eventId)
        fun fromEntry(entry: NavBackStackEntry): Long {
            return entry.arguments?.getLong(ARG_EVENT_ID) ?: 0L
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavHostController.navigateToAddNewEvent()  =
    this.navigate(NavRoutes.AddNewEvent.route)
