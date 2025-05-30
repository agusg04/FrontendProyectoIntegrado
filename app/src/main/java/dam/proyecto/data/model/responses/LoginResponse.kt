package dam.proyecto.data.model.responses

data class LoginResponse(
    val nombre: String,
    val accessToken: String,
    val refreshToken: String
)
