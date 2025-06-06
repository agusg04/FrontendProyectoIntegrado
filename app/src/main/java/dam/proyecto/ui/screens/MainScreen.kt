package dam.proyecto.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dam.proyecto.ui.components.BottomBar
import dam.proyecto.ui.screens.app.HomeScreen
import dam.proyecto.ui.screens.app.ProfileScreen
import dam.proyecto.ui.screens.app.WallScreen
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun MainScreen(
    globalNavController: NavHostController,
    authViewModel: AuthViewModel

) {
     val localNavController = rememberNavController()

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomBar(localNavController) }
    ) { innerPadding ->

        NavHost(
            localNavController,
            startDestination = "home",
            Modifier.padding(innerPadding),
            route = "main_graph", // clave para restoreState
        ) {
            composable("home") { HomeScreen(localNavController, globalNavController, authViewModel) }
            composable("wall") { WallScreen(authViewModel) }
            composable("profile") { ProfileScreen(authViewModel) }
        }
    }
}
