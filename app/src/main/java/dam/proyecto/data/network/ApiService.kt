package dam.proyecto.data.network

import dam.proyecto.data.model.ApiResponse
import dam.proyecto.data.model.LoginRequest
import dam.proyecto.data.model.LoginResponse
import dam.proyecto.data.model.RallyData
import dam.proyecto.data.model.RegisterRequest
import dam.proyecto.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<RegisterResponse>

    @POST("api/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Void>

    @GET("api/rally")
    suspend fun rallyInfo(): ApiResponse<RallyData>
}