package dam.proyecto.data.repository

import dam.proyecto.data.model.ListaUsuarios
import dam.proyecto.data.model.UserData
import dam.proyecto.data.model.UsuarioRegistradoDto
import dam.proyecto.data.model.UsuarioRegistroDto
import dam.proyecto.data.network.RetrofitClient.api

class UserRepository {

    suspend fun getUsers(): Result<ListaUsuarios> {
        return try {
            val response = api.getUsers()

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(idUsuario: Long): Result<UserData> {
        return try {
            val response = api.getUser(idUsuario)

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(idUsuario: Long, usuarioRegistroDto: UsuarioRegistroDto): Result<UsuarioRegistradoDto> {
        return try {
            val response = api.updateUser(idUsuario, usuarioRegistroDto)

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
