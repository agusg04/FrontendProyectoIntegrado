package dam.proyecto.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dam.proyecto.data.model.UserData
import dam.proyecto.data.util.Constants

@Composable
fun DialogVerUsuario(
    usuario: UserData,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                /*IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }*/

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (!usuario.urlFoto.isNullOrEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Constants.BASE_URL + usuario.urlFoto)
                                .crossfade(true)
                                .memoryCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                                .diskCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                                .build(),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            imageVector = Icons.Default.AccountCircle, // icono por defecto
                            contentDescription = "Foto de perfil por defecto",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoItem(label = "Email", value = usuario.email)
                    InfoItem(label = "Nombre", value = usuario.nombre)
                    InfoItem(label = "Primer Apellido", value = usuario.primerApellido)
                    InfoItem(label = "Segundo Apellido", value = usuario.segundoApellido)
                    InfoItem(label = "Rol", value = usuario.rol.name)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier,
                        text = "Fotos subidas: ${usuario.fotosSubidas.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Votos emitidos: ${usuario.votos.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


            }

        }
    }


}

@Composable
private fun InfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
    }
}
