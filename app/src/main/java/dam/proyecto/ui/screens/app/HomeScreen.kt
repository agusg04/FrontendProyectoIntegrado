package dam.proyecto.ui.screens.app

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dam.proyecto.ui.components.ErrorBox
import dam.proyecto.ui.components.RallyInfoCard
import dam.proyecto.ui.components.StyledButton
import dam.proyecto.ui.components.SuccessBox
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    localNavController: NavController,
    globalNavController: NavController,
    authViewModel: AuthViewModel
) {
    LaunchedEffect(Unit) {
        authViewModel.requestRallyInfo()
    }

    BackHandler(enabled = true) {}

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authViewModel.logoutSuccess) {
        if (authViewModel.logoutSuccess == true) {
            successMessage = "Has cerrado sesión"
            authViewModel.cleanLoginErrors()
            authViewModel.resetLogoutState()
        }
    }

    LaunchedEffect(authViewModel.loginSuccess) {
        if (authViewModel.loginSuccess == true) {
            successMessage = "Has iniciado sesión correctamente"
            authViewModel.cleanLoginErrors()
            authViewModel.resetLoginState()
        }
    }

    LaunchedEffect(authViewModel.registerSuccess) {
        if (authViewModel.registerSuccess == true) {
            successMessage = "Registro exitoso. ¡Bienvenido!"
            authViewModel.cleanRegisterErrors()
            authViewModel.resetRegisterState()
        }
    }

    if (authViewModel.rallyData == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x00FFFFFF)), // Fondo semitransparente
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF878FA3))
        }
        return
    }

    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter

    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            authViewModel.rallyData?.let {
                RallyInfoCard(it)
            }

            StyledButton(
                onClick = {
                    if (!authViewModel.isLoggedIn) {
                        globalNavController.navigate("login")
                    } else {
                        errorMessage = "Ya has iniciado sesión"
                    }
                },
                text = "Iniciar Sesión",
            )

            StyledButton(
                onClick = {
                    if (!authViewModel.isLoggedIn) {
                        globalNavController.navigate("register")
                    } else {
                        errorMessage = "Ya estás registrado e identificado"
                    }
                },
                text = "Registrarse",
            )

            StyledButton(
                onClick = {
                    if (authViewModel.isLoggedIn) {
                        authViewModel.logout()
                    } else {
                        errorMessage = "No puedes cerrar sesión si no has iniciado una"
                    }
                },
                text = "Cerrar Sesión"
            )

            StyledButton(
                onClick = {
                    localNavController.navigate("wall") {
                        popUpTo(localNavController.graph.startDestinationRoute ?: "home") {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                text = "Ir al muro"
            )
        }

        errorMessage?.let {
            ErrorBox(
                message = it,
                onErrorClosed = { errorMessage = null }
            )
        }

        successMessage?.let {
            SuccessBox(
                message = it,
                onMessageClosed = { successMessage = null }
            )
        }
    }
}