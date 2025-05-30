package dam.proyecto.data.model

data class RallyData(
    val nombreRally: String,
    val descripcionRally: String,
    val fechaInicio: String,
    val fechaFin: String,
    val plazoVotacion: String,
    val maxVotosUsuario: Int,
    val maxFotosUsuario: Int,
    val primerPremio: Int,
    val segundoPremio: Int,
    val tercerPremio: Int
)
