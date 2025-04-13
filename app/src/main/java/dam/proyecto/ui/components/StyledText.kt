package dam.proyecto.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dam.proyecto.R

@Composable
fun StyledText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 40.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight,
            fontFamily = FontFamily(Font(R.font.amoria))
        )
    )
}