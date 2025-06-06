package dam.proyecto.data.model

import dam.proyecto.data.model.enums.Roles
import java.io.Serializable

data class UsuarioRegistroDto(
    var email: String,
    var nombre: String,
    var apellido1: String,
    var apellido2: String,
    var contrasenia: String,
    var rol: Roles,
    var urlFoto: String,
    var fechaRegistro: String
) : Serializable
