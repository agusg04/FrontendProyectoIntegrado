package dam.proyecto

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dam.proyecto.navigation.AppNavigation
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun MyApp(authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    AppNavigation(navController = navController, authViewModel = authViewModel)
}