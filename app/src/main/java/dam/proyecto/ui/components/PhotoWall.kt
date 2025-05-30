package dam.proyecto.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dam.proyecto.data.model.FotografiaPost

@Composable
fun PhotoWall(
    posts: List<FotografiaPost>,
    onVoteClicked: ((Long) -> Unit)? = null,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { "${it.id}-${it.votada}" }
        ) { post ->
            PhotoCard(
                nombreUsuario = post.nombreUsuario ?: "No disponible",
                apellidoUsuario = post.apellidoUsuario ?: "No disponible",
                titulo = post.titulo ?: "No disponible",
                descripcion = post.descripcion ?: "No disponible",
                imageUrl = post.filePath ?: "",
                totalVotos = post.resultadoPuntajeTotal ?: 0,
                votada = post.votada ?: false,
                onVoteClick = {
                    post.id?.let {
                        if (onVoteClicked != null) {
                            onVoteClicked(it)
                        }
                    }
                }
            )
        }
    }
}