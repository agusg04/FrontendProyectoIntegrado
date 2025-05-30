package dam.proyecto.data.model.requests

data class LogoutRequest(
    val accessToken: String,
    val refreshToken: String
)