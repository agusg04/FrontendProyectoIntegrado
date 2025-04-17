package dam.proyecto.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.proyecto.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    var isLoggedIn by mutableStateOf(false)
        private set

    var isGuest by mutableStateOf(false)
        private set

    var username by mutableStateOf<String?>(null)
        private set

    var token by mutableStateOf<String?>(null)
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    var registerError by mutableStateOf<String?>(null)
        private set

    var loginState by mutableStateOf<Boolean?>(null)
        private set

    var registerState by mutableStateOf<Boolean?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            loginError = "Rellene ambos campos"
            return
        }
        loginError = null
        isLoading = true

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            isLoading = false
            result.onSuccess { response ->
                isLoggedIn = true
                isGuest = false
                username = response.nombre
                token = response.token
                loginError = null
                loginState = true

            }.onFailure { error ->
                loginError = error.message ?: "Error desconocido"
                isLoggedIn = false
                token = null
            }

        }
    }

    fun register(
        name: String, lastName1: String,
        lastName2: String, email: String,
        password: String, passwordAgain: String
    ) {

        if (password != passwordAgain) {
            registerError = "Las contraseñas no coinciden"
            return
        }

        registerError = null

        if (listOf(
                name, lastName1, lastName2, email, password
            ).any { it.isBlank() }
        ) {
            registerError = "Rellene todos los campos"
            return
        }

        registerError = null
        isLoading = true

        viewModelScope.launch {
            val result = authRepository.register(name, lastName1, lastName2, email, password)
            isLoading = false
            result.onSuccess { response ->
                isLoggedIn = true
                isGuest = false
                username = response.nombre
                token = response.token
                registerError = null
                registerState = true

            }.onFailure { error ->
                registerError = error.message ?: "Error desconocido"
                isLoggedIn = false
                token = null
            }
        }
    }

    fun enterAsGuest() {
        username = null
        isGuest = true
        isLoggedIn = false
        token = null
        loginError = null
    }

    fun logout() {
        username = null
        isLoggedIn = false
        isGuest = false
        token = null
        loginError = null
    }

    fun cleanErrors() {
        loginError = null
        loginState = null
        registerError = null
        registerState = null
    }
}