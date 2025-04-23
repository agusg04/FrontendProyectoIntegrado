package dam.proyecto.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.proyecto.data.model.RallyData
import dam.proyecto.data.repository.AuthRepository
import dam.proyecto.data.repository.RallyRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val rallyRepository = RallyRepository()

    var username by mutableStateOf<String?>(null)
        private set

    //RALLY
    var rallyData by mutableStateOf<RallyData?>(null)
        private set

    var nombreRally by mutableStateOf<String?>(null)
        private set

    var descripcionRally by mutableStateOf<String?>(null)
        private set

    var fechaInicio by mutableStateOf<String?>(null)
        private set

    var fechaFin by mutableStateOf<String?>(null)
        private set

    var plazoVotacion by mutableStateOf<String?>(null)
        private set

    var votosPorUsuario by mutableStateOf<Int?>(null)
        private set

    var maxFotosUsuario by mutableStateOf<Int?>(null)
        private set

    var primerPremio by mutableStateOf<Int?>(null)
        private set

    var segundoPremio by mutableStateOf<Int?>(null)
        private set

    var tercerPremio by mutableStateOf<Int?>(null)
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    var isGuest by mutableStateOf(false)
        private set

    var token by mutableStateOf<String?>(null)
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    var registerError by mutableStateOf<String?>(null)
        private set

    var apiError by mutableStateOf<String?>(null)
        private set

    var logOutError by mutableStateOf<String?>(null)
        private set

    var loginSuccess by mutableStateOf<Boolean?>(null)

    var registerSuccess by mutableStateOf<Boolean?>(null)

    var logoutSuccess by mutableStateOf<Boolean?>(null)

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
                onAuthSuccess(response.nombre, response.token)
                loginError = null
                loginSuccess = true
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

        if (listOf(name, lastName1, lastName2, email, password).any { it.isBlank() }) {
            registerError = "Rellene todos los campos"
            return
        }

        registerError = null
        isLoading = true

        viewModelScope.launch {
            val result = authRepository.register(name, lastName1, lastName2, email, password)
            isLoading = false
            result.onSuccess { response ->
                onAuthSuccess(response.nombre, response.token)
                registerError = null
                registerSuccess = true

            }.onFailure { error ->
                registerError = error.message ?: "Error desconocido"
                isLoggedIn = false
                token = null
            }
        }
    }

    fun logout() {
        isLoading = true
        logoutSuccess = null
        logOutError = null

        viewModelScope.launch {
            val currentToken = token

            if (currentToken != null) {
                val result = authRepository.logout(currentToken)
                result.onFailure { error ->
                    logOutError = error.message ?: "Error al cerrar sesión"
                }
            }

            username = null
            isLoggedIn = false
            isGuest = false
            token = null
            isLoading = false
            logoutSuccess = true
        }
    }


    fun requestRallyInfo(
    ) {
        viewModelScope.launch {
            isLoading = true
            val result = rallyRepository.requestRallyInfo()
            isLoading = false
            result.onSuccess { data ->
                rallyData = data

                nombreRally = data.nombreRally
                descripcionRally = data.descripcionRally
                fechaInicio = data.fechaInicio
                fechaFin = data.fechaFin
                plazoVotacion = data.plazoVotacion
                votosPorUsuario = data.votosPorUsuario
                maxFotosUsuario = data.maxFotosUsuario
                primerPremio = data.primerPremio
                segundoPremio = data.segundoPremio
                tercerPremio = data.tercerPremio

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
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

    fun cleanLoginErrors() {
        loginError = null
    }

    fun cleanRegisterErrors() {
        registerError = null
    }

    fun resetLoginState() {
        loginError = null
        loginSuccess = false
    }

    fun resetRegisterState() {
        registerError = null
        registerSuccess = false
    }

    fun resetLogoutState() {
        logOutError = null
        logoutSuccess = false
    }

    private fun onAuthSuccess(name: String, authToken: String) {
        isLoggedIn = true
        isGuest = false
        username = name
        token = authToken
    }
}