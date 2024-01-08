package br.com.rafaelleal.minhasferias.app.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.rafaelleal.minhasferias.presentation_common.sealed.FriendsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_friends.ui.list.FriendsListScreen


const val GRAPH_ROUTE_FRIENDS = "graph_friends"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.friendsNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = FriendsNavRoutes.Friends.route,
        route = GRAPH_ROUTE_FRIENDS
    ) {
        composable(route = FriendsNavRoutes.Friends.route) {
            FriendsListScreen(
//                hiltViewModel(),
//                navController
            ) { }
        }
    }
}