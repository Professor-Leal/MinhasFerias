package br.com.rafaelleal.minhasferias.presentation_common.sealed

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument


private const val ROUTE_FRIENDS = "friends"
private const val ROUTE_ADD_FRIEND = "add-new-friend"
private const val ROUTE_FRIEND = "friend/%s"
private const val ARG_FRIEND_ID = "friendId"

sealed class FriendsNavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Friends : FriendsNavRoutes(ROUTE_FRIENDS)

    object AddNewFriend : FriendsNavRoutes(ROUTE_ADD_FRIEND)

    object Friend : FriendsNavRoutes(
        route = String.format(ROUTE_FRIEND, "{$ARG_FRIEND_ID}"),
        arguments = listOf(navArgument(ARG_FRIEND_ID) {
            type = NavType.LongType
        })
    ) {
        fun routeForFriend(friendId: Long) = String.format(ROUTE_FRIEND, friendId)
        fun fromEntry(entry: NavBackStackEntry): Long {
            return entry.arguments?.getLong(ARG_FRIEND_ID) ?: 0L
        }
    }
}

fun NavHostController.navigateToFriends() =
    this.navigate(FriendsNavRoutes.Friends.route) {
        popUpTo(FriendsNavRoutes.Friends.route)
        launchSingleTop = true
    }

fun NavHostController.navigateToAddNewFriend()  =
    this.navigate(FriendsNavRoutes.AddNewFriend.route)

fun NavHostController.navigateToEditFriend(id: Long) =
    this.navigate(FriendsNavRoutes.Friend.routeForFriend(id))
