package dam.proyecto.ui.navigation

import dam.proyecto.R

sealed class BottomNavItem(
    val route: String,
    val icon: Int
) {
    object Home : BottomNavItem("home", R.drawable.home_24px)
    object Wall : BottomNavItem("wall", R.drawable.wallpaper_slideshow_24px)
    object Profile : BottomNavItem("profile", R.drawable.person_24px)

    companion object {
        val items = listOf(Home, Wall, Profile)
    }
}
