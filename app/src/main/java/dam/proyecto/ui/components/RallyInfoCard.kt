package dam.proyecto.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.proyecto.data.model.RallyData
import java.time.format.DateTimeFormatter

@Composable
fun RallyInfoCard(rally: RallyData?) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StyledText(
                text = rally?.nombreRally ?: "No disponible",
                fontSize = 40.sp
            )
            Text(rally?.descripcionRally ?: "No disponible", fontSize = 20.sp)

            InfoLabel(
                "Fecha de inicio",
                rally?.fechaInicio?.formatFecha() ?: "No disponible",
                Color(0xFF333333),
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Fecha de fin",
                rally?.fechaFin?.formatFecha() ?: "No disponible",
                Color(0xFF333333),
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Plazo de votación",
                rally?.plazoVotacion?.formatFecha() ?: "No disponible",
                Color(0xFF333333),
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Votos por usuario",
                (rally?.maxVotosUsuario ?: "No disponible").toString(),
                Color(0xFF333333),
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Máx. fotos por usuario",
                (rally?.maxFotosUsuario ?: "No disponible").toString(),
                Color(0xFF333333),
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Primer premio",
                (rally?.primerPremio ?: "No disponible").toString(),
                Color(0xFF333333),
                suffix = " €",
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Segundo premio",
                (rally?.segundoPremio ?: "No disponible").toString(),
                Color(0xFF333333),
                suffix = " €",
                boldLabel = false,
                boldContent = true
            )

            InfoLabel(
                "Tercer premio",
                (rally?.tercerPremio ?: "No disponible").toString(),
                Color(0xFF333333),
                suffix = " €",
                boldLabel = false,
                boldContent = true
            )
        }
    }

}

fun String?.formatFecha(): String {
    return try {
        this?.let {
            val localDateTime = java.time.LocalDateTime.parse(it)
            localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        } ?: "No disponible"
    } catch (e: Exception) {
        "No disponible"
    }
}

