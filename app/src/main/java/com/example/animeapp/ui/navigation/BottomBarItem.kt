package com.example.animeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Search: BottomBarItem(
        route = "search",
        title = "Search",
        icon =  Icons.Default.Search
    )

    object Favorites: BottomBarItem(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )
}
