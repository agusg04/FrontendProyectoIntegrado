package dam.proyecto.ui.screens.app

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dam.proyecto.ui.components.ErrorBox
import dam.proyecto.ui.components.PhotoWall
import dam.proyecto.ui.components.SuccessBox
import dam.proyecto.ui.viewmodel.AuthViewModel

@Composable
fun WallScreen(
    authViewModel: AuthViewModel
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        authViewModel.requestPhotos()
        if (authViewModel.isLoggedIn) {
            authViewModel.requestVotes()
        }
    }

    LaunchedEffect(authViewModel.listaFotos, authViewModel.listaVotos) {
        if (
            authViewModel.isLoggedIn &&
            authViewModel.listaFotos != null &&
            authViewModel.listaVotos != null
        ) {
            authViewModel.cargarVotos()
        }
    }

    BackHandler(enabled = true) {}

    LaunchedEffect(authViewModel.votoError) {
        if (authViewModel.votoError != null) {
            errorMessage = authViewModel.votoError
            authViewModel.cleanVoteErrors()
        }
    }

    if (!authViewModel.datosCargados || authViewModel.isRefreshingData) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x00FFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF878FA3))
        }
    } else {
        val fotos by remember { derivedStateOf { authViewModel.listaFotos } }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (fotos != null) {
                    fotos?.let {
                        PhotoWall(
                            it,
                            onVoteClicked = if (authViewModel.isLoggedIn) { postId ->
                                val post = fotos?.find { f -> f.id == postId }
                                if (post?.votada == true) {
                                    authViewModel.quitarVoto(postId)
                                } else {
                                    authViewModel.votar(postId)
                                }
                            } else null
                        )
                    }
                }
            }

            errorMessage?.let {
                ErrorBox(
                    message = it,
                    onErrorClosed = { errorMessage = null }
                )
            }

            successMessage?.let {
                SuccessBox(
                    message = it,
                    onMessageClosed = { successMessage = null }
                )
            }
        }
    }
}