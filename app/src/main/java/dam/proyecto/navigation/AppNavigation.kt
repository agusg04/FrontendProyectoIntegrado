package dam.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dam.proyecto.ui.screens.LoginScreen
import dam.proyecto.ui.screens.MainScreen
import dam.proyecto.ui.screens.RegisterScreen
import dam.proyecto.ui.screens.WelcomeScreen
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController, authViewModel)
        }
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("register") {
            RegisterScreen(navController, authViewModel)
        }
        /*
        composable("main") {
            MainScreen(navController, authViewModel)
        }

         */
    }
}
