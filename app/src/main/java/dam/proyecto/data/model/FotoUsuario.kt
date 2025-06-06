package dam.proyecto.data.model

import dam.proyecto.data.model.enums.Estado

data class FotoUsuario(
    val fotoId: Long,
    val urlFoto: String,
    val votos: Int? = null,
    val titulo: String,
    val descripcion: String? = null,
    val estado: Estado
)
