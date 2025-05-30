package dam.proyecto.data.model.responses

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T? = null
)
