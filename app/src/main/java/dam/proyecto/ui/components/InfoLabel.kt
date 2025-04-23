package dam.proyecto.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun InfoLabel(
    label: String,
    content: String,
    contentColor: Color = Color(0xFF1565C0),
    boldLabel: Boolean = true,
    boldContent: Boolean = true,
    suffix: String = ""
) {
    Row {
        Text(
            text = "$label: ",
            fontWeight = if (boldLabel) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = content + suffix,
            color = contentColor,
            fontWeight = if (boldContent) FontWeight.Bold else FontWeight.Normal
        )
    }
}
