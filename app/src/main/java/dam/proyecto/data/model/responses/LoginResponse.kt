package dam.proyecto.data.model.responses

import dam.proyecto.data.model.UserData

data class LoginResponse(
    val userData: UserData,
    val accessToken: String,
    val refreshToken: String,
)
