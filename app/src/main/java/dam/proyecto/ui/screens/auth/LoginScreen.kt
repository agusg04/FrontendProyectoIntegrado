package dam.proyecto.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dam.proyecto.ui.components.ErrorBox
import dam.proyecto.ui.components.buttons.StyledButton
import dam.proyecto.ui.components.StyledText
import dam.proyecto.ui.components.StyledTextField
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        authViewModel.cleanLoginErrors()
    }

    Scaffold(
        containerColor = Color.White,
        content = { innerpadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
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
                            imeAction = ImeAction.Next
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
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    StyledButton(
                        onClick = { authViewModel.login(email, password) },
                        text = "Iniciar Sesión",
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    StyledButton(
                        onClick = { navController.popBackStack() },
                        text = "Volver"
                    )
                }

                authViewModel.loginError?.let { error ->
                    ErrorBox(
                        message = error,
                        onErrorClosed = { authViewModel.cleanLoginErrors() })
                }
            }
        }
    )

    LaunchedEffect(authViewModel.loginSuccess) {
        if (authViewModel.loginSuccess == true) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
            authViewModel.cleanLoginErrors()
        }
    }
}
