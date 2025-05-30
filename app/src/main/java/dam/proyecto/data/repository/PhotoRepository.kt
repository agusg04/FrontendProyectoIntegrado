package dam.proyecto.data.repository

import dam.proyecto.data.model.FotografiaWallData
import dam.proyecto.data.network.RetrofitClient.api

class PhotoRepository {

    suspend fun getAllPhotos(): Result<FotografiaWallData> {
        return try {
            val response = api.getPhotos()

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