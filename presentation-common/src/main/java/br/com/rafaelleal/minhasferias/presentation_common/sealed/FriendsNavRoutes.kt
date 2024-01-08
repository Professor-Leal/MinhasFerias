package br.com.rafaelleal.minhasferias.presentation_common.sealed

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController


private const val ROUTE_FRIENDS = "friends"

sealed class FriendsNavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Friends : RegisteredEventsNavRoutes(ROUTE_FRIENDS)
}

fun NavHostController.navigateToFriends() =
    this.navigate(FriendsNavRoutes.Friends.route)


