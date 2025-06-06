package dam.proyecto.data.network

import dam.proyecto.data.model.FotografiaWallData
import dam.proyecto.data.model.ListaUsuarios
import dam.proyecto.data.model.ListaVotos
import dam.proyecto.data.model.responses.ApiResponse
import dam.proyecto.data.model.requests.LoginRequest
import dam.proyecto.data.model.responses.LoginResponse
import dam.proyecto.data.model.requests.LogoutRequest
import dam.proyecto.data.model.RallyData
import dam.proyecto.data.model.UserData
import dam.proyecto.data.model.UsuarioRegistradoDto
import dam.proyecto.data.model.UsuarioRegistroDto
import dam.proyecto.data.model.requests.RefreshTokenRequest
import dam.proyecto.data.model.requests.RegisterRequest
import dam.proyecto.data.model.requests.VoteRequest
import dam.proyecto.data.model.responses.RefreshTokenResponse
import dam.proyecto.data.model.responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/")
    suspend fun checkServerStatus(): Response<Boolean>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<RegisterResponse>

    @POST("api/auth/logout")
    suspend fun logout(@Body logoutRequest: LogoutRequest): ApiResponse<String>

    @POST("api/auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): ApiResponse<RefreshTokenResponse>

    @GET("api/rally")
    suspend fun getRallyInfo(): ApiResponse<RallyData>

    @GET("api/photos")
    suspend fun getPhotos(): ApiResponse<FotografiaWallData>

    @GET("api/votes")
    suspend fun getVotes(): ApiResponse<ListaVotos>

    @POST("api/votes")
    suspend fun vote(@Body voteRequest: VoteRequest): ApiResponse<Boolean>

    @HTTP(method = "DELETE", path = "api/votes", hasBody = true)
    suspend fun removeVote(@Body voteRequest: VoteRequest): ApiResponse<Boolean>

    @GET("api/users")
    suspend fun getUsers(): ApiResponse<ListaUsuarios>

    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") userId: Long): ApiResponse<UserData>

    @PUT("api/users/{id}")
    suspend fun updateUser(@Path("id") userId: Long, @Body usuarioRegistroDto: UsuarioRegistroDto): ApiResponse<UsuarioRegistradoDto>
}