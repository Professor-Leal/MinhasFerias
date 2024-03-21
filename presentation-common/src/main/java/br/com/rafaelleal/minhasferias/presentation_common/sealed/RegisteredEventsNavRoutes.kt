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
private const val ROUTE_ADD_FRIENDS_TO_EVENT = "add-friends-to-event/%s"
private const val ARG_ADD_FRIENDS_TO_EVENT_ID = "eventId"

sealed class RegisteredEventsNavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Events : RegisteredEventsNavRoutes(ROUTE_EVENTS)

    object AddNewEvent : RegisteredEventsNavRoutes(ROUTE_ADD_EVENT)

    object Event : RegisteredEventsNavRoutes(
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

    object AddFriends : RegisteredEventsNavRoutes(
        route = String.format(ROUTE_ADD_FRIENDS_TO_EVENT, "{$ARG_ADD_FRIENDS_TO_EVENT_ID}"),
        arguments = listOf(navArgument(ARG_ADD_FRIENDS_TO_EVENT_ID) {
            type = NavType.LongType
        })
    ) {
        fun routeForAddFriendToEvent(eventId: Long) =
            String.format(ROUTE_ADD_FRIENDS_TO_EVENT, eventId)

        fun fromEntry(entry: NavBackStackEntry): Long {
            return entry.arguments?.getLong(ARG_ADD_FRIENDS_TO_EVENT_ID) ?: 0L
        }
    }
}


fun NavHostController.navigateToHome() =
    this.navigate(RegisteredEventsNavRoutes.Events.route) {
        popUpTo(RegisteredEventsNavRoutes.Events.route)
        launchSingleTop = true
    }

fun NavHostController.navigateToAddNewEvent() =
    this.navigate(RegisteredEventsNavRoutes.AddNewEvent.route)

fun NavHostController.navigateToEditEvent(id: Long) =
    this.navigate(RegisteredEventsNavRoutes.Event.routeForEvent(id))

fun NavHostController.navigateToAddFriendsToEvent(id: Long) =
    this.navigate(RegisteredEventsNavRoutes.AddFriends.routeForAddFriendToEvent(id))