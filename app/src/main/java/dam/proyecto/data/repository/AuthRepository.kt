package dam.proyecto.data.repository

import dam.proyecto.data.model.LoginRequest
import dam.proyecto.data.model.LoginResponse
import dam.proyecto.data.model.RegisterRequest
import dam.proyecto.data.model.RegisterResponse
import dam.proyecto.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {

    private val api: ApiService = Retrofit.Builder()
        .baseUrl("https://5d5b-47-62-46-218.ngrok-free.app/") // Cambia a la IP real si usas un dispositivo físico
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        name: String, lastName1: String,
        lastName2: String, email: String,
        password: String
    ): Result<RegisterResponse> {
        return try {
            val response = api.register(RegisterRequest(name, lastName1, lastName2, email, password))

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
