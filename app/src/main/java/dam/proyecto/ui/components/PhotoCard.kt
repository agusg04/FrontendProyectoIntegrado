package dam.proyecto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dam.proyecto.data.util.Constants

@Composable
fun PhotoCard(
    nombreUsuario: String,
    apellidoUsuario: String,
    titulo: String,
    descripcion: String,
    imageUrl: String,
    totalVotos: Int,
    votada: Boolean,
    onVoteClick: () -> Unit
) {
    val showFullImage = remember { mutableStateOf(false) }
    val votadaState = rememberUpdatedState(newValue = votada)
    val totalVotosState = rememberUpdatedState(newValue = totalVotos)

    if (showFullImage.value) {
        Dialog(
            onDismissRequest = { showFullImage.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.8f))
                    .clickable { showFullImage.value = false },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.BASE_URL + imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen Ampliada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            // Nombre de usuario
            Text(
                text = "$nombreUsuario $apellidoUsuario",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp)
            )

            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp)
            )

            // Imagen

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.BASE_URL + imageUrl)
                    .crossfade(true)
                    .memoryCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                    .diskCachePolicy(CachePolicy.DISABLED) //DESACTIVAR
                    .build(),
                contentDescription = "Fotografia",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable{ showFullImage.value = true },
                contentScale = ContentScale.Crop
            )



            // Descripción
            Text(
                text = descripcion,
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            // Votos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = onVoteClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Votar",
                        tint = if (votadaState.value) Color.Red else Color.Gray
                    )
                }
                Text(
                    text = totalVotosState.value.toString(),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}
