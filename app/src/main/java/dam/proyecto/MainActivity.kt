package dam.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dam.proyecto.ui.theme.RallyFotograficoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RallyFotograficoAppTheme {
                MyApp()
            }
        }
    }

    /*
    override fun onDestroy() {
        super.onDestroy()
    }

     */
    /*
    override fun onStop() {
        super.onStop()
    }
     */
    /*
    override fun onStart() {
        super.onStart()
    }
    
     */
}