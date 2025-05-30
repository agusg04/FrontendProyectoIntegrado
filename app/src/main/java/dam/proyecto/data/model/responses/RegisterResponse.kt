package dam.proyecto.data.model.responses

data class RegisterResponse(
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val email: String,
    val fechaRegistro: String,
    val accessToken: String,
    val refreshToken: String
)