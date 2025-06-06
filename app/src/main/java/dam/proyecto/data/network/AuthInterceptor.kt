package dam.proyecto.data.network

import dam.proyecto.data.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authRepository: AuthRepository,
    private val getAccessToken: () -> String?,
    private val getRefreshToken: () -> String?,
    private val onAccessTokenRefreshed: (String) -> Unit
): Interceptor {

    val rutasProtegidas = listOf(
        "api/votes",
        "api/users"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.encodedPath

        var request = originalRequest

        val necesitaToken =  rutasProtegidas.any { url.contains(it) }

        if (necesitaToken) {
            getAccessToken()?.let { token ->
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
        }

        val response = chain.proceed(request)

        if (response.code == 403) {
            response.close()

            val refreshToken = getRefreshToken() ?: return response

            val nuevoAccessToken = runBlocking {
                authRepository.refreshToken(refreshToken).getOrNull()?.accessToken
            } ?: return response

            //Si despues de pedir el refresco no funciona hacer logout y volver a la pantalla principal

            onAccessTokenRefreshed(nuevoAccessToken)

            val newRequest = request.newBuilder()
                .header("Authorization", "Bearer $nuevoAccessToken")
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }
}