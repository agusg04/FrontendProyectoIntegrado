package dam.proyecto.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dam.proyecto.data.model.UserData
import dam.proyecto.data.model.UsuarioRegistroDto
import dam.proyecto.data.model.enums.Roles
import dam.proyecto.ui.components.ErrorBox
import dam.proyecto.ui.components.StyledTextField
import dam.proyecto.ui.components.buttons.StyledButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogEditarUsuario(
    usuarioModificado: UserData,
    onGuardar: (UsuarioRegistroDto) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: MutableState<String>,
    onErrorConsumed: () -> Unit
) {
    val nombre = remember { mutableStateOf("") }
    val apellido1 = remember { mutableStateOf("") }
    val apellido2 = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val urlFoto = remember { mutableStateOf("") }
    val rol = remember { mutableStateOf(usuarioModificado.rol) }
    val fechaTexto = remember { mutableStateOf("") }
    val errorFecha = remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    val camposInvalidos = remember { mutableStateOf(false) }

    LaunchedEffect(usuarioModificado) {
        nombre.value = usuarioModificado.nombre
        apellido1.value = usuarioModificado.primerApellido
        apellido2.value = usuarioModificado.segundoApellido
        email.value = usuarioModificado.email
        password.value = ""
        urlFoto.value = usuarioModificado.urlFoto ?: ""
        fechaTexto.value = try {
            LocalDateTime.parse(usuarioModificado.fechaRegistro).format(formatter)
        } catch (e: Exception) {
            ""
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
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
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Editar usuario",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    StyledTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = "*Email",
                        paddingHorizontal = 0.dp,
                    )

                    StyledTextField(
                        value = nombre.value,
                        onValueChange = { nombre.value = it },
                        label = "*Nombre",
                        paddingHorizontal = 0.dp,
                    )

                    StyledTextField(
                        value = apellido1.value,
                        onValueChange = { apellido1.value = it },
                        label = "*Primer Apellido",
                        paddingHorizontal = 0.dp,
                    )

                    StyledTextField(
                        value = apellido2.value,
                        onValueChange = { apellido2.value = it },
                        label = "*Segundo Apellido",
                        paddingHorizontal = 0.dp,
                    )

                    StyledTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = "Cambiar contraseña",
                        paddingHorizontal = 0.dp,
                    )

                    StyledTextField(
                        value = urlFoto.value,
                        onValueChange = { urlFoto.value = it },
                        label = "Ruta Foto",
                        paddingHorizontal = 0.dp
                    )

                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                        StyledTextField(
                            readOnly = true,
                            value = rol.value.name,
                            onValueChange = {},
                            label = "*Rol",
                            paddingHorizontal = 0.dp,
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Roles.entries.forEach { roleOption ->
                                DropdownMenuItem(
                                    text = { Text(roleOption.name) },
                                    onClick = {
                                        rol.value = roleOption
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = fechaTexto.value,
                        onValueChange = {
                            fechaTexto.value = it
                            errorFecha.value = false
                        },
                        label = { Text("Fecha registro", color = Color.Black) },
                        isError = errorFecha.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    if (errorFecha.value) {
                        Text(
                            "Formato inválido. Ejemplo válido: 05/06/2025 14:30:00",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (camposInvalidos.value) {
                        Text(
                            "Por favor, completa todos los campos obligatorios.",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancelar", color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        StyledButton(
                            "Guardar",
                            onClick = {
                                if (email.value.isBlank() || nombre.value.isBlank() || apellido1.value.isBlank() || apellido2.value.isBlank()) {
                                    camposInvalidos.value = true
                                    return@StyledButton
                                }

                                camposInvalidos.value = false

                                try {
                                    val parsedFecha = LocalDateTime.parse(fechaTexto.value, formatter)
                                    val usuarioEditado = UsuarioRegistroDto(
                                        email = email.value,
                                        nombre = nombre.value,
                                        apellido1 = apellido1.value,
                                        apellido2 = apellido2.value,
                                        contrasenia = password.value,
                                        rol = rol.value,
                                        urlFoto = urlFoto.value,
                                        fechaRegistro = parsedFecha.toString()
                                    )
                                    onGuardar(usuarioEditado)
                                } catch (e: Exception) {
                                    errorFecha.value = true
                                }
                            },
                            paddingHorizontal = 0.dp
                        )
                    }
                }
            }

            if (errorMessage.value.isNotBlank()) {
                ErrorBox(
                    message = errorMessage.value,
                    onErrorClosed = { onErrorConsumed() }
                )
            }
        }
    }
}
