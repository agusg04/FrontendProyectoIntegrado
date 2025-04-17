package dam.proyecto.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dam.proyecto.R
import dam.proyecto.ui.components.StyledButton
import dam.proyecto.ui.components.StyledText
import dam.proyecto.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                    Text(
                        text = "Fotográfico",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.amoria))
                        )
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
                        onClick = { navController.navigate("main") },
                        text = "Acceder como invitado",
                    )
                }
            }
        }
    )
}