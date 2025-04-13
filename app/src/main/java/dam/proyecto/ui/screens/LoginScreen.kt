package dam.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.focus.FocusDirection
import dam.proyecto.ui.components.StyledButton
import dam.proyecto.ui.components.StyledText
import dam.proyecto.ui.components.StyledTextField
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = Color.White,
        content = { innerpadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StyledText(
                    text = "Iniciar Sesión",
                    fontSize = 40.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(20.dp))

                StyledTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo electrónico",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                StyledTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                StyledButton(
                    onClick = {  },
                    text = "Iniciar Sesión",
                )

                Spacer(modifier = Modifier.height(10.dp))

                StyledButton(
                    onClick = { navController.navigate("welcome") },
                    text = "Volver"
                )
            }

        }
    )

}
