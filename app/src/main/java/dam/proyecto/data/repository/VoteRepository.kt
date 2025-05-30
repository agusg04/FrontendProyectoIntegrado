package dam.proyecto.data.repository

import dam.proyecto.data.model.ListaVotos
import dam.proyecto.data.model.requests.VoteRequest
import dam.proyecto.data.network.RetrofitClient.api

class VoteRepository {

    suspend fun getVotes(): Result<ListaVotos> {
        return try {
            val response = api.getVotes()

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun vote(idFoto: Long): Result<Boolean> {
        return try {
            val response = api.vote(VoteRequest(idFoto))

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun removeVote(idFoto: Long): Result<Boolean> {
        return try {
            val response = api.removeVote(VoteRequest(idFoto))

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