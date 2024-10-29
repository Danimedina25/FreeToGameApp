package com.danifitdev.freetogameapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.danifitdev.freetogameapp.R
import com.danifitdev.freetogameapp.domain.model.GameModel
import com.danifitdev.freetogameapp.ui.viewmodel.GamesViewModel

@Composable
fun ListOfGamesScreen(navController: NavController, viewModel: GamesViewModel = hiltViewModel(),) {
    viewModel.getGamesFromDb()
    val games by viewModel.games.collectAsState()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val snackbarHostState = remember { SnackbarHostState() }

    var searchQuery by remember { mutableStateOf("") }

    val filteredGames = games.filter { game ->
        game.title!!.contains(searchQuery, ignoreCase = true) ||
                game.genre!!.contains(searchQuery, ignoreCase = true)
    }
    LaunchedEffect(savedStateHandle?.get<String>(DELETE_RESULT_KEY)) {
        savedStateHandle?.get<String>(DELETE_RESULT_KEY)?.let { message ->
            snackbarHostState.showSnackbar(message)
            savedStateHandle.remove<String>(DELETE_RESULT_KEY) // Elimina el mensaje después de mostrarlo
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState, modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.Bottom)
            .padding(bottom = 100.dp)) },
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(color = Color.Black)// Altura personalizada
            ) {
                Text(text = "Bienvenido",
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge ,
                    color = Color.White)
                // TextField para la búsqueda
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { newText ->
                        searchQuery = newText
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ícono de búsqueda",
                            tint = Color.White
                        )
                    },
                    label = { Text(text = "Buscar por nombre o género", style = MaterialTheme.typography.labelSmall) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 20.dp),
                    shape = RoundedCornerShape(24.dp), // Cambia el redondeado aquí
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.DarkGray,
                        backgroundColor = Color.Transparent,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        textColor = Color.White
                    ),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge
                )

            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.Black, Color.LightGray),
                            center = Offset(300f, 100f), // Centro del degradado
                            radius = 3000f
                        )
                    ),
            ) {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.padding(0.dp)
                ) {
                    items(filteredGames) { game ->
                        GameCard(game = game, onClick = { navController.navigate("game_detail_screen/${game.id}") })
                    }
                }
            }
        }
    )
}

@Composable
fun GameCard(game: GameModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 10.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna de la imagen
            Image(
                painter = rememberAsyncImagePainter(model = game.thumbnail),
                contentDescription = game.title,
                modifier = Modifier
                    .width(210.dp)
                    .height(110.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.FillBounds
            )

            // Columna para título y género
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = game.title!!,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Género: ${game.genre}",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 2.dp),
                ) {
                    var platformIcon: Painter? = null
                    var platformIcon2: Painter? = null

                    when (game.platform) {
                        "PC (Windows)" -> {
                            platformIcon = painterResource(id = R.drawable.icon_pc_windows)
                        }
                        "Web Browser" -> {
                            platformIcon = painterResource(id = R.drawable.icon_web_browser)
                        }
                        "PC (Windows), Web Browser" -> {
                            platformIcon = painterResource(id = R.drawable.icon_pc_windows)
                            platformIcon2 = painterResource(id = R.drawable.icon_web_browser)
                        }
                    }

                    if (platformIcon != null) {
                        Box(modifier = Modifier.padding(end = 10.dp)){
                            Image(
                                painter = platformIcon,
                                contentDescription = "${game.platform} Icon",
                                modifier = Modifier.size(18.dp)
                            )
                        }

                    }
                    if (platformIcon2 != null) {
                        Box {
                            Image(
                                painter = platformIcon2,
                                contentDescription = "${game.platform} Icon",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
