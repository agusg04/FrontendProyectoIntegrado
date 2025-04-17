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
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun MainScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("wall") { WallScreen() }
            composable("profile") {
                if (authViewModel.isLoggedIn) {
                    //UserProfileScreen()
                } else {
                    //GuestProfileScreen()
                }
            }

        }
    }
}
