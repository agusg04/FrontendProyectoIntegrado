package dam.proyecto.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dam.proyecto.ui.components.StyledButton
import dam.proyecto.ui.components.StyledText
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun WelcomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    Scaffold(
        containerColor = Color.White,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StyledText(
                        text = "Rally",
                        fontSize = 50.sp,
                        color = Color.Black
                    )
                    StyledText(
                        text = "Fotográfico",
                        fontSize = 50.sp,
                        color = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StyledButton(
                        onClick = { navController.navigate("login") },
                        text = "Iniciar Sesión",
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    StyledButton(
                        onClick = { navController.navigate("register") },
                        text = "Registrarse",
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    StyledButton(
                        onClick = { navController.navigate("main")
                                    authViewModel.enterAsGuest() },
                        text = "Acceder como invitado",
                    )
                }
            }
        }
    )
}