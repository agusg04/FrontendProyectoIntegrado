package dam.proyecto.ui.components.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dam.proyecto.data.model.UsuarioId
import dam.proyecto.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun DialogUsuariosAdmin(
    usuarios: List<UsuarioId>,
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit,
    onCrear: () -> Unit
) {
    val usuarioSeleccionado = remember { mutableStateOf<UsuarioId?>(null) }
    val mostrarDialogVerUsuario = remember { mutableStateOf(false) }
    val mostrarDialogEditarUsuario = remember { mutableStateOf(false) }
    val showError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val userDataCrud = authViewModel.userDataCrud

    LaunchedEffect(userDataCrud) {
        if (userDataCrud != null && usuarioSeleccionado != null && mostrarDialogVerUsuario.value) {
            mostrarDialogVerUsuario.value = true
        }
    }


    LaunchedEffect(userDataCrud) {
        if (userDataCrud != null && usuarioSeleccionado != null && mostrarDialogEditarUsuario.value) {
            mostrarDialogEditarUsuario.value = true
        }
    }

    Column {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .heightIn(min = 300.dp, max = 600.dp)
                ) {
                    Text(
                        "Gestión de usuarios",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState)
                    ) {

                        usuarios.forEach { usuario ->
                            val isSelected = usuarioSeleccionado.value == usuario
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { usuarioSeleccionado.value = usuario }
                                    .border(
                                        2.dp,
                                        if (isSelected) Color.Gray else Color.LightGray,
                                        RoundedCornerShape(8.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) Color(0xFFEEEEEE) else Color.White
                                )
                            ) {
                                Text(
                                    text = usuario.email,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val buttonColors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            disabledContainerColor = Color.LightGray,
                            disabledContentColor = Color.White
                        )

                        Button(
                            onClick = {
                                usuarioSeleccionado.value?.let { usuario ->
                                    scope.launch {
                                        authViewModel.requestUser(usuario.idUsuario)
                                        mostrarDialogVerUsuario.value = true
                                    }
                                }
                            },
                            enabled = usuarioSeleccionado.value != null,
                            modifier = Modifier.fillMaxWidth(),
                            colors = buttonColors
                        ) {
                            Text("Ver datos usuario")
                        }

                        Button(
                            onClick = {
                                usuarioSeleccionado.value?.let { usuario ->
                                    scope.launch {
                                        authViewModel.requestUser(usuario.idUsuario)
                                        mostrarDialogEditarUsuario.value = true
                                    }
                                }
                            },
                            enabled = usuarioSeleccionado.value != null,
                            modifier = Modifier.fillMaxWidth(),
                            colors = buttonColors
                        ) {
                            Text("Editar datos usuario")
                        }

                        Button(
                            onClick = { usuarioSeleccionado.value?.let({}) },
                            enabled = usuarioSeleccionado.value != null,
                            modifier = Modifier.fillMaxWidth(),
                            colors = buttonColors
                        ) {
                            Text("Eliminar usuario")
                        }

                        Button(
                            onClick = onCrear,
                            modifier = Modifier.fillMaxWidth(),
                            colors = buttonColors
                        ) {
                            Text("Crear nuevo usuario")
                        }
                    }
                }
            }
        }
    }



    if (mostrarDialogVerUsuario.value && usuarioSeleccionado.value != null && userDataCrud != null) {
        DialogVerUsuario(
            usuario = userDataCrud,
            onDismiss = {
                mostrarDialogVerUsuario.value = false
                authViewModel.userDataCrud = null
            }
        )
    }

    if (mostrarDialogEditarUsuario.value && usuarioSeleccionado.value != null && userDataCrud != null) {
        DialogEditarUsuario(
            usuarioModificado = userDataCrud,
            onGuardar = { usuarioEditado ->
                usuarioSeleccionado.value?.let { usuarioId ->
                    scope.launch {
                        authViewModel.updateUser(usuarioId.idUsuario, usuarioEditado)
                    }
                }
            },
            onDismiss = {
                mostrarDialogEditarUsuario.value = false
                authViewModel.userDataCrud = null
            },
            errorMessage = errorMessage,
            onErrorConsumed = {
                showError.value = false
                errorMessage.value = ""
            }
        )
    }

    LaunchedEffect(authViewModel.userUpdateSuccess.value) {
        when (authViewModel.userUpdateSuccess.value) {
            true -> {
                println("Usuario guardado: ${userDataCrud?.email}")
                mostrarDialogEditarUsuario.value = false
                authViewModel.userDataCrud = null
                authViewModel.userUpdateSuccess.value = null
            }
            false -> {
                showError.value = true
                errorMessage.value = authViewModel.apiError ?: "Error desconocido"
                authViewModel.userUpdateSuccess.value = null
            }
            null -> {
                // No hacer nada
            }
        }
    }
}
