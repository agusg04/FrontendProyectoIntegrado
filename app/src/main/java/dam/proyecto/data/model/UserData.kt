package dam.proyecto.data.model

import dam.proyecto.data.model.enums.Roles

data class UserData(
    val email: String,
    val nombre: String,
    val primerApellido: String,
    val segundoApellido: String,
    val rol: Roles,
    val urlFoto: String? = null,
    val fechaRegistro: String,
    val fotosSubidas: Set<FotoUsuario>,
    val votos: Set<VotoUsuario>,
)
