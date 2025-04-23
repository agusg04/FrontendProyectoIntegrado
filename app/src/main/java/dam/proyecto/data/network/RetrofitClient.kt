package dam.proyecto.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: ApiService = Retrofit.Builder()
        .baseUrl("https://29dd-77-211-7-136.ngrok-free.app/") // Cambia a la IP real si usas un dispositivo físico
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
