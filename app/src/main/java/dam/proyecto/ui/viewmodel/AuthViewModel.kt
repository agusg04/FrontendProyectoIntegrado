package dam.proyecto.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    // Estado de si el usuario está logueado
    var isLoggedIn by mutableStateOf(false)
        private set

    // Estado de si es un invitado
    var isGuest by mutableStateOf(false)
        private set

    // Nombre de usuario o cualquier dato
    var username by mutableStateOf<String?>(null)
        private set

    // Lógica para loguearse
    fun login(username: String) {
        this.username = username
        isLoggedIn = true
        isGuest = false
    }

    // Lógica para entrar como invitado
    fun enterAsGuest() {
        username = null
        isGuest = true
        isLoggedIn = false
    }

    // Lógica para cerrar sesión
    fun logout() {
        username = null
        isLoggedIn = false
        isGuest = false
    }
}