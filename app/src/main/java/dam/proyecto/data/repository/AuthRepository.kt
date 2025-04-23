package dam.proyecto.data.repository

import dam.proyecto.data.model.LoginRequest
import dam.proyecto.data.model.LoginResponse
import dam.proyecto.data.model.RegisterRequest
import dam.proyecto.data.model.RegisterResponse
import dam.proyecto.data.network.RetrofitClient.api

class AuthRepository {

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

    suspend fun logout(token: String): Result<Unit> {
        return try {
            val response = api.logout("Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error del servidor: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
