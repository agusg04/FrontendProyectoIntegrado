package dam.proyecto.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.proyecto.data.model.FotografiaPost
import dam.proyecto.data.model.RallyData
import dam.proyecto.data.model.UserData
import dam.proyecto.data.model.UsuarioId
import dam.proyecto.data.model.UsuarioRegistroDto
import dam.proyecto.data.model.enums.Roles
import dam.proyecto.data.model.responses.LoginResponse
import dam.proyecto.data.model.responses.RegisterResponse
import dam.proyecto.data.network.RetrofitClient
import dam.proyecto.data.repository.AuthRepository
import dam.proyecto.data.repository.PhotoRepository
import dam.proyecto.data.repository.RallyRepository
import dam.proyecto.data.repository.UserRepository
import dam.proyecto.data.repository.VoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val rallyRepository = RallyRepository()
    private val photoRepository = PhotoRepository()
    private val voteRepository = VoteRepository()
    private val userRepository = UserRepository()

    var accessToken by mutableStateOf<String?>(null)
        private set

    var refreshToken by mutableStateOf<String?>(null)
        private set

    //DATOS USUARIO PROPIO
    var userData by mutableStateOf<UserData?>(null)
        private set
    //FIN DATOS USUARIO PROPIO

    //DATOS USUARIO CRUD
    var userDataCrud by mutableStateOf<UserData?>(null)

    val userUpdateSuccess = mutableStateOf<Boolean?>(null)
    //FIN DATOS USUARIO CRUD

    //RALLY
    var rallyData by mutableStateOf<RallyData?>(null)
        private set
    //FIN RALLY

    //FOTOS
    var listaFotos by mutableStateOf<List<FotografiaPost>?>(null)

    var listaVotos by mutableStateOf<List<Long>?>(null)
    //FIN FOTOS

    //USUARIOS
    var listaUsuarios by mutableStateOf<List<UsuarioId>?>(null)

    //FIN USUARIOS

    var isLoggedIn by mutableStateOf(false)
        private set

    var isGuest by mutableStateOf(false)
        private set

    //ERRORES
    var loginError by mutableStateOf<String?>(null)
        private set

    var registerError by mutableStateOf<String?>(null)
        private set

    var apiError by mutableStateOf<String?>(null)
        private set

    var logOutError by mutableStateOf<String?>(null)
        private set

    var showServerError by mutableStateOf(false)
        private set

    var votoError by mutableStateOf<String?>(null)
        private set
    //FIN ERRORES

    var loginSuccess by mutableStateOf<Boolean?>(false)

    var registerSuccess by mutableStateOf<Boolean?>(false)

    var logoutSuccess by mutableStateOf<Boolean?>(false)

    var isLoading by mutableStateOf(false)
        private set

    var showServerSuccess by mutableStateOf<Boolean?>(false)
        private set

    var isServerAlive by mutableStateOf<Boolean?>(null)
        private set

    var isRefreshingData by mutableStateOf(false)
        private set

    private var serverCheckJob: Job? = null

    private var lastServerStatus: Boolean? = null

    val datosCargados: Boolean
        get() = listaFotos != null && (!isLoggedIn || (userData?.votos.isNullOrEmpty() || listaVotos != null))

    private fun validateEmptyFields(vararg fields: String): String? {
        return if (fields.any { it.isBlank() }) {
            "Rellene todos los campos"
        } else {
            null
        }
    }

    private fun validateEmail(email: String): String? {
        return if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Email no válido"
        } else {
            null
        }
    }

    fun checkServerStatus() {
        viewModelScope.launch {
            isLoading = true
            val result = authRepository.checkServerStatus()
            isLoading = false

            result.onSuccess { alive ->
                if (lastServerStatus != null) {

                    if (lastServerStatus == false && alive) {
                        showServerSuccess = true
                        showServerError = false
                    }

                    else if (lastServerStatus == true && !alive) {
                        showServerError = true
                        showServerSuccess = false
                    }
                } else {

                    if (!alive) {
                        showServerError = true
                    } //else {
                        //showServerSuccess = true
                    //}
                }
                lastServerStatus = alive
                isServerAlive = alive

            }.onFailure {
                if (lastServerStatus != false) {
                    showServerError = true
                    showServerSuccess = false
                }
                lastServerStatus = false
                isServerAlive = false
            }
        }
    }

    fun startCheckingServerStatus(intervalMillis: Long = 5000L) {
        if (serverCheckJob != null) return

        serverCheckJob = viewModelScope.launch {
            checkServerStatus()
            delay(intervalMillis)
            while (true) {
                checkServerStatus()
                delay(intervalMillis)
            }
        }
    }

    fun stopCheckingServerStatus() {
        serverCheckJob?.cancel()
        serverCheckJob = null
    }

    fun login(email: String, password: String) {
        loginError = validateEmptyFields(email, password)
        if (loginError != null) return

        loginError = validateEmail(email)
        if (loginError != null) return

        isLoading = true

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            isLoading = false
            result.onSuccess { response ->
                onAuthSuccess(response)
                loginError = null
                loginSuccess = true
            }.onFailure { error ->
                loginError = error.message ?: "Error desconocido"
                isLoggedIn = false
                accessToken = null
                refreshToken = null
            }
        }
    }

    fun register(
        name: String, lastName1: String,
        lastName2: String, email: String,
        password: String, passwordAgain: String
    ) {
        registerError = validateEmptyFields(name, lastName1, lastName2, email, password)
        if (registerError != null) return

        if (password != passwordAgain) {
            registerError = "Las contraseñas no coinciden"
            return
        }

        registerError = validateEmail(email)
        if (registerError != null) return
        
        isLoading = true

        viewModelScope.launch {
            val result = authRepository.register(name, lastName1, lastName2, email, password)
            isLoading = false
            result.onSuccess { response ->
                onAuthSuccess(response)
                registerError = null
                registerSuccess = true

            }.onFailure { error ->
                registerError = error.message ?: "Error desconocido"
                isLoggedIn = false
                accessToken = null
                refreshToken = null
            }
        }
    }

    fun logout() {
        isLoading = true
        logoutSuccess = null
        logOutError = null
        listaFotos = null
        listaVotos = null

        if (accessToken == null && refreshToken == null) {
            logOutError = "Error al cerrar sesión"
            isLoading = false
            return
        }

        viewModelScope.launch {
            val currentAccessToken = accessToken
            val currentRefreshToken = refreshToken

            if (currentAccessToken != null && currentRefreshToken != null) {
                val result = authRepository.logout(currentAccessToken, currentRefreshToken)

                result.onSuccess {
                    listaVotos = null

                }.onFailure { error ->
                    logOutError = error.message ?: "Error al cerrar sesión"
                }

                stopCheckingServerStatus()
                userData = null
                isLoggedIn = false
                isGuest = false
                accessToken = null
                refreshToken = null
                isLoading = false
                logoutSuccess = true
            }
        }
    }


    fun requestRallyInfo() {
        viewModelScope.launch {
            isRefreshingData = true
            isLoading = true
            val result = rallyRepository.getRallyInfo()
            isLoading = false
            result.onSuccess { data ->
                rallyData = data

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
            }
            isRefreshingData = false
        }
    }

    fun requestVotes() {
        viewModelScope.launch {
            isRefreshingData = true
            isLoading = true
            val result = voteRepository.getVotes()
            isLoading = false
            result.onSuccess { data ->
                listaVotos = data.idsFotosVotadas.toList()

                apiError = null
            }.onFailure { error->
                apiError = error.message ?: "Error desconocido"
            }
            isRefreshingData = false
        }
    }

    fun requestPhotos() {
        viewModelScope.launch {
            isLoading = true
            val result = photoRepository.getAllPhotos()
            isLoading = false
            result.onSuccess { data ->
                listaFotos = data.photos.toList()

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
            }
        }
    }

    fun requestUsers() {
        viewModelScope.launch {
            isLoading = true
            val result = userRepository.getUsers()
            isLoading = false
            result.onSuccess { data ->
                listaUsuarios = data.usuarios.toList()

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
            }
        }
    }

    fun requestUser(idUsuario: Long) {
        viewModelScope.launch {
            isLoading = true
            val result = userRepository.getUser(idUsuario)
            isLoading = false
            result.onSuccess { data ->
                userDataCrud = data

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
                userDataCrud = null
            }
        }
    }

    fun updateUser(idUsuario: Long, usuarioEditado: UsuarioRegistroDto) {
        viewModelScope.launch {
            isLoading = true
            val result = userRepository.updateUser(idUsuario, usuarioEditado)
            isLoading = false
            result.onSuccess { data ->
                val antiguos = userDataCrud

                if (antiguos != null) {
                    userDataCrud = data.toUserData(
                        votos = antiguos.votos,
                        fotosSubidas = antiguos.fotosSubidas
                    )
                }
                userUpdateSuccess.value = true
            }.onFailure { error ->
                userUpdateSuccess.value = false
                apiError = error.message ?: "Error desconocido"
            }
        }
    }

    fun votar(idFoto: Long) {
        val maxVotos = rallyData?.maxVotosUsuario
        val numVotosActuales = listaVotos?.size ?: 0

        if (maxVotos != null && numVotosActuales >= maxVotos) {
            votoError = "Límite de votos alcanzado"
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = voteRepository.vote(idFoto)
            isLoading = false
            result.onSuccess {

                val votosActuales = listaVotos?.toMutableList() ?: mutableListOf()
                if (!votosActuales.contains(idFoto)) {
                    votosActuales.add(idFoto)
                }
                listaVotos = votosActuales

                listaFotos = listaFotos?.map { foto ->
                    if (foto.id == idFoto) {
                        foto.copy(
                            votada = true,
                            resultadoPuntajeTotal = (foto.resultadoPuntajeTotal ?: 0) + 1
                        )
                    } else {
                        foto
                    }
                }?.toList()

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
            }
        }
    }

    fun quitarVoto(idFoto: Long) {
        viewModelScope.launch {
            isLoading = true
            val result = voteRepository.removeVote(idFoto)
            println(result)
            isLoading = false
            result.onSuccess {

                val votosActuales = listaVotos?.toMutableList() ?: mutableListOf()
                if (votosActuales.contains(idFoto)) {
                    votosActuales.remove(idFoto)
                }
                listaVotos = votosActuales

                listaFotos = listaFotos?.map { foto ->
                    if (foto.id == idFoto) {
                        foto.copy(
                            votada = false,
                            resultadoPuntajeTotal = (foto.resultadoPuntajeTotal ?: 0) - 1
                        )
                    } else {
                        foto
                    }
                }?.toList()

                apiError = null
            }.onFailure { error ->
                apiError = error.message ?: "Error desconocido"
            }
        }
    }


    fun cargarVotos() {
        val votos = listaVotos ?: return
        val fotos = listaFotos ?: return

        val nuevasFotos = fotos.map { foto ->
            foto.copy(votada = votos.contains(foto.id))
        }

        listaFotos = nuevasFotos
    }

    fun enterAsGuest() {
        userData = null
        isGuest = true
        isLoggedIn = false
        accessToken = null
        refreshToken = null
        loginError = null
    }

    fun cleanLoginErrors() {
        loginError = null
    }

    fun cleanRegisterErrors() {
        registerError = null
    }

    fun cleanVoteErrors() {
        votoError = null
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

    private fun onAuthSuccess(loginData: LoginResponse) {
        isLoggedIn = true
        isGuest = false
        userData = loginData.userData
        asignarTokens(loginData.accessToken, loginData.refreshToken)

        listaFotos = null
        listaVotos = null
    }

    private fun onAuthSuccess(registerData: RegisterResponse) {
        isLoggedIn = true
        isGuest = false
        userData = UserData(
            registerData.email,
            registerData.nombre,
            registerData.apellido1,
            registerData.apellido2,
            Roles.USER,
            null,
            registerData.fechaRegistro,
            emptySet(),
            emptySet(),
        )
        asignarTokens(registerData.accessToken, registerData.refreshToken)

        listaFotos = null
        listaVotos = null
    }

    private fun asignarTokens(accessToken: String, refreshToken: String? = null) {
        this.accessToken = accessToken
        println("Token de acceso asignado: $accessToken")
        refreshToken?.let {
            this.refreshToken = it
            println("Token de refresco asignado: $refreshToken")
        }
        RetrofitClient.accessTokenProvider = { this.accessToken }
        RetrofitClient.refreshTokenProvider = { this.refreshToken }
        RetrofitClient.onAccessTokenRefreshed = { nuevoAccessToken ->
            this.accessToken = nuevoAccessToken
        }
    }

    fun dismissServerMessages() {
        showServerError = false
        showServerSuccess = false
    }
}