package dam.proyecto.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun BottomBar(navController: NavController) {
    val items = listOf("home", "wall", "profile")
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { },
                selected = false,
                onClick = { navController.navigate(screen) }
            )
        }
    }
}
