package dam.proyecto.data.model

import dam.proyecto.data.model.enums.Roles
import java.io.Serializable

data class UsuarioRegistradoDto(
    var id: Long,
    var email: String,
    var nombre: String,
    var apellido1: String,
    var apellido2: String,
    var rol: Roles,
    var urlFoto: String,
    var fechaRegistro: String
): Serializable {

    fun toUserData(
        votos: Set<VotoUsuario> = emptySet(),
        fotosSubidas: Set<FotoUsuario> = emptySet()
    ): UserData {

        return UserData(
            email = this.email,
            nombre = this.nombre,
            primerApellido = this.apellido1,
            segundoApellido = this.apellido2,
            rol = this.rol,
            urlFoto = this.urlFoto,
            fechaRegistro = this.fechaRegistro,
            votos = votos,
            fotosSubidas = fotosSubidas
        )
    }
}
