package br.com.rafaelleal.minhasferias.app.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.rafaelleal.minhasferias.presentation_common.sealed.FriendsNavRoutes
import br.com.rafaelleal.minhasferias.presentation_common.sealed.RegisteredEventsNavRoutes

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Events : BottomBarScreen(
        route = GRAPH_ROUTE_EVENTS,
        title = "Events",
        icon = Icons.Default.Place
    )
    object Friends : BottomBarScreen(
        route = GRAPH_ROUTE_FRIENDS,
        title = "Friends",
        icon = Icons.Default.Person
    )
}
