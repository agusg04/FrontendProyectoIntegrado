package dam.proyecto.data.network

import dam.proyecto.data.repository.AuthRepository
import dam.proyecto.data.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    var accessTokenProvider: () -> String? = { null }
    var refreshTokenProvider: (() -> String?) = { null }
    var onAccessTokenRefreshed: ((String) -> Unit) = {}

    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(
            AuthInterceptor(
                authRepository = AuthRepository(),
                getAccessToken = { accessTokenProvider() },
                getRefreshToken = { refreshTokenProvider() },
                onAccessTokenRefreshed = { nuevoToken ->
                    println("Token refrescado: $nuevoToken")
                    onAccessTokenRefreshed(nuevoToken)
                }
            )
        ).build()
    }

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService = instance.create(ApiService::class.java)
}
