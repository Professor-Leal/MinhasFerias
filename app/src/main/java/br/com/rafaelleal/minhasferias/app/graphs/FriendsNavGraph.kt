package br.com.rafaelleal.minhasferias.app.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.rafaelleal.minhasferias.presentation_common.sealed.FriendsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToAddNewFriend
import br.com.rafaelleal.minhasferias.presentation_common.sealed.navigateToFriends
import br.com.rafaelleal.minhasferias.presentation_friends.ui.list.FriendsListScreen
import br.com.rafaelleal.minhasferias.presentation_friends.ui.single.AddFriendScreen
import br.com.rafaelleal.minhasferias.presentation_friends.ui.single.EditFriendScreen


const val GRAPH_ROUTE_FRIENDS = "graph_friends"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.friendsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = GRAPH_ROUTE_FRIENDS,
        startDestination = FriendsNavRoutes.Friends.route
    ) {
        composable(
            route = FriendsNavRoutes.Friends.route
        ) {
            FriendsListScreen(
                hiltViewModel(),
                navController
            ) { navController.navigateToAddNewFriend() }
        }
        composable(
            route = FriendsNavRoutes.AddNewFriend.route,
        ) {
            AddFriendScreen(
                hiltViewModel()
            ) { navController.navigateToFriends() }
        }
        composable(
            route = FriendsNavRoutes.Friend.route,
            arguments = FriendsNavRoutes.Friend.arguments
        ) {
            EditFriendScreen(
                FriendsNavRoutes.Friend.fromEntry(it),
                hiltViewModel()
            ) { navController.navigateToFriends() }
        }
    }
}