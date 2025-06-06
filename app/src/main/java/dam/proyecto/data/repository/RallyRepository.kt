package dam.proyecto.data.repository

import dam.proyecto.data.model.RallyData
import dam.proyecto.data.network.RetrofitClient.api

class RallyRepository {

    suspend fun getRallyInfo(): Result<RallyData> {
        return try {
            val response = api.getRallyInfo()

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