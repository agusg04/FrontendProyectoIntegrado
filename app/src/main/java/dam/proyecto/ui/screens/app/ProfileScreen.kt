package dam.proyecto.ui.screens.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dam.proyecto.data.model.enums.Roles
import dam.proyecto.data.util.Constants
import dam.proyecto.ui.components.dialogs.DialogAddPhoto
import dam.proyecto.ui.components.buttons.ContainedButton
import dam.proyecto.ui.components.dialogs.DialogUsuariosAdmin
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel
) {
    val userData = authViewModel.userData
    val showAddPhotosDialog = remember { mutableStateOf(false) }
    val showUserDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        authViewModel.requestUsers()
    }

    if (showAddPhotosDialog.value) {
        Dialog(
            onDismissRequest = { showAddPhotosDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            DialogAddPhoto(
                onDismiss = { showAddPhotosDialog.value = false},
                onCameraClick = {},
                onGalleryClick = {}
            )
        }
    }

    if (showUserDialog.value) {
        DialogUsuariosAdmin(
            usuarios = authViewModel.listaUsuarios ?: emptyList(),
            onDismiss = { showUserDialog.value = false },
            onCrear = {  },
            authViewModel = authViewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Foto de perfil centrada
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if(userData?.urlFoto != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.BASE_URL + userData.urlFoto)
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                        .diskCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                        .build(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                        //.border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.FillWidth
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Foto por defecto",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                        //.border(2.dp, Color.Gray, CircleShape),
                    tint = Color(0xFF8890A4)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista horizontal de fotos

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(
                items = userData?.fotosSubidas?.toList() ?: emptyList(),
                key = { it.fotoId }
            ) { foto ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.BASE_URL + foto.urlFoto)
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                        .diskCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                        .build(),
                    contentDescription = "Foto del rally",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Botón para agregar nueva foto
            item {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .clickable { showAddPhotosDialog.value = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Agregar foto",
                        tint = Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }



        Spacer(modifier = Modifier.height(24.dp))
        /*
        // Datos del usuario
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: ${user.name}", style = MaterialTheme.typography.bodyLarge)
                Text("Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onEditProfile,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Editar perfil")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

         */

        // Panel admin si corresponde
        if (userData?.rol == Roles.ADMIN) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
                border = BorderStroke(1.dp, Color.Red),
                ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Panel de administrador",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ContainedButton(
                        "Modificar usuarios",
                        { showUserDialog.value = true },
                        modifier = Modifier
                    )

                    ContainedButton(
                        "Modificar rally",
                        {},
                        modifier = Modifier
                    )

                    ContainedButton(
                        "Modificar fotografías",
                        {},
                        modifier = Modifier
                    )

                    ContainedButton(
                        "Ver estadísticas y resultados",
                        {},
                        modifier = Modifier
                    )

                    ContainedButton(
                        "Validar fotos",
                        {},
                        modifier = Modifier
                    )
                }
            }
        }
    }
}