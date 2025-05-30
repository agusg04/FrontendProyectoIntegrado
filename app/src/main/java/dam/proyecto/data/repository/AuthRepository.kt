package dam.proyecto.data.repository

import dam.proyecto.data.model.requests.LoginRequest
import dam.proyecto.data.model.requests.LogoutRequest
import dam.proyecto.data.model.requests.RefreshTokenRequest
import dam.proyecto.data.model.responses.LoginResponse
import dam.proyecto.data.model.requests.RegisterRequest
import dam.proyecto.data.model.responses.RefreshTokenResponse
import dam.proyecto.data.model.responses.RegisterResponse
import dam.proyecto.data.network.RetrofitClient.api
import org.json.JSONObject
import retrofit2.HttpException

class AuthRepository {

    suspend fun checkServerStatus(): Result<Boolean> {
        return try {
            val response = api.checkServerStatus()
            if (response.isSuccessful && response.body() == true) {
                Result.success(true)
            } else {
                Result.failure(Exception("Servidor no repondio correctamente"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            val errorMsg = e.response()?.errorBody()?.string()
                ?.let { JSONObject(it).getString("message") }
          /*val errorMsg = e.response()?.errorBody()?.string()?.let { parseErrorMessage(it) }
                ?: "Error desconocido"*/
            Result.failure(Exception(errorMsg))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        name: String,
        lastName1: String,
        lastName2: String,
        email: String,
        password: String
    ): Result<RegisterResponse> {
        return try {
            val response =
                api.register(RegisterRequest(name, lastName1, lastName2, email, password))

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            val errorMsg = e.response()?.errorBody()?.string()
                ?.let { JSONObject(it).getString("message") }
            /*val errorMsg = e.response()?.errorBody()?.string()?.let { parseErrorMessage(it) }
                  ?: "Error desconocido"*/
            Result.failure(Exception(errorMsg))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(accessToken: String, refreshToken: String): Result<Unit> {
        return try {
            val logoutRequest = LogoutRequest(
                accessToken = "Bearer $accessToken",
                refreshToken = "Bearer $refreshToken"
            )
            val response = api.logout(logoutRequest)
            if (response.success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refreshToken(refreshToken: String): Result<RefreshTokenResponse> {
        return try {
            val response = api.refreshToken(RefreshTokenRequest(refreshToken))

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun parseErrorMessage(json: String): String {
        return try {
            val jsonObject = JSONObject(json)
            jsonObject.optString("message", "Error desconocido")
        } catch (e: Exception) {
            "Error desconocido"
        }
    }

}
