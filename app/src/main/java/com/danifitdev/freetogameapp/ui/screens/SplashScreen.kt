package com.danifitdev.freetogameapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danifitdev.freetogameapp.R
import com.danifitdev.freetogameapp.ui.viewmodel.GamesViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit, viewModel: GamesViewModel = hiltViewModel()) {
    val successMessage by viewModel.successMessage.collectAsState(null)
    val errorMessage by viewModel.errorMessage.collectAsState(null)
    val context = LocalContext.current

    // Temporizador para simular el SplashScreen (2 segundos)
    LaunchedEffect(Unit) {
        viewModel.fetchGames()
        delay(2000)
        onNavigateToMain()
    }

    LaunchedEffect(successMessage) {
        if(successMessage != null){
          showToast(context, successMessage!!)
        }
    }
    LaunchedEffect(errorMessage) {
        if(errorMessage != null){
            showToast(context, errorMessage!!)
        }
    }

    // Box para centrar el contenido
    Box(modifier = Modifier.fillMaxSize()
        .background(
           color = Color.Black
        ), contentAlignment = Alignment.Center) {
        // Cargar y mostrar la imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.splashscreen), // Ruta a la imagen en drawable
            contentDescription = "Splash Screen Image",
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentScale = ContentScale.Fit// Abarcar toda la pantalla
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onNavigateToMain = {})
}

// Función para mostrar el Toast
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}