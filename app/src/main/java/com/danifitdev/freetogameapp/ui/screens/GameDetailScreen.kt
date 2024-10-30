package com.danifitdev.freetogameapp.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.danifitdev.freetogameapp.ui.theme.BlueButton
import com.danifitdev.freetogameapp.ui.theme.RedButton
import com.danifitdev.freetogameapp.ui.viewmodel.GamesViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlin.math.min


const val DELETE_RESULT_KEY = "delete_result"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameDetailScreen(
    idGame: Int,
    navController: NavHostController,
    viewModel: GamesViewModel = hiltViewModel(),
) {
    viewModel.getGamesById(idGame)
    val game by viewModel.game.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var editedPlatform by remember { mutableStateOf("") }
    var editedGenre by remember { mutableStateOf("") }
    var editedDeveloper by remember { mutableStateOf("") }
    var editedPublisher by remember { mutableStateOf("") }
    var editedRelaseDate by remember { mutableStateOf("") }
    val showDialogDelete = remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showImageDialog by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(successMessage) {
        successMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearMessages()
        }
    }
    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White // O el color específico que desees
                ),
                title = {
                    Text(
                        text = game!!.title!!, color = Color.Black, textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.Black
                        )
                    }
                },
            )
        },

    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.Black, Color.LightGray),
                        center = Offset(300f, 100f), // Centro del degradado
                        radius = 3000f
                    )
                )
                .padding(top = 100.dp, start = 16.dp, end = 16.dp, bottom = 100.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showImageDialog = true } // Abre el DatePicker
            ) {
                Image(
                    painter = rememberImagePainter(game!!.thumbnail),
                    contentDescription = "${game!!.title} thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = game!!.short_description!!,
                fontSize = 16.sp,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2D2D3D), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                if(isLoading){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    ) {
                        CircularProgressIndicator()
                    }
                }else{
                    if(!isEditing){
                        GameInfoRow(label = "Género", value = game!!.genre!!)
                        GameInfoRow(label = "Plataforma", value = game!!.platform!!)
                        GameInfoRow(label = "Publicador", value = game!!.publisher!!)
                        GameInfoRow(label = "Desarrollador", value = game!!.developer!!)
                        GameInfoRow(
                            label = "Fecha de Lanzamiento",
                            value = formatDate(game!!.release_date!!)
                        )
                    }else{

                        CustomTextField(
                            editedGenre,
                            {editedGenre = it},
                            "Género"
                        )
                        CustomTextField(
                            value = editedPlatform,
                            onValueChange = { editedPlatform = it },
                            label = "Plataforma"
                        )
                        CustomTextField(
                            value = editedPublisher,
                            onValueChange = { editedPublisher = it },
                            label = "Publicador"
                        )

                        CustomTextField(
                            value = editedDeveloper,
                            onValueChange = { editedDeveloper = it },
                            label = "Desarrollador"
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDatePicker = true } // Abre el DatePicker
                        ) {
                            CustomTextFieldDate(editedRelaseDate, {editedRelaseDate = it}, "Fecha de lanzamiento")
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)) {
                    if(isEditing){
                        BlueButton(onClick = {
                            viewModel.updateGame(
                                game!!.copy(
                                    genre = editedGenre,
                                    platform = editedPlatform,
                                    publisher = editedPublisher,
                                    developer = editedDeveloper,
                                    release_date = editedRelaseDate
                                )
                            )
                            isEditing = false
                        }, Modifier, "Guardar")
                    }else{
                        BlueButton(onClick = {
                            // Inicia el modo de edición
                            editedPlatform = game!!.platform!!
                            editedGenre = game!!.genre!!
                            editedDeveloper = game!!.developer!!
                            editedPublisher = game!!.publisher!!
                            editedRelaseDate = game!!.release_date!!
                            isEditing = true
                        }, Modifier, "Editar")
                    }

                }
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)) {
                    if(isEditing){
                        RedButton(onClick = {
                            isEditing = false
                        }, Modifier, "Cancelar")
                    }else{
                        RedButton(onClick = {
                            showDialogDelete.value = true // Mostrar diálogo de confirmación
                        }, Modifier, text = "Eliminar")
                    }
                }
            }
        }
    }
    if (showDialogDelete.value) {
        AlertDialog(
            onDismissRequest = { showDialogDelete.value = false },
            title = {
                Text(text = "Confirmar eliminación")
            },
            text = {
                Text("¿Estás seguro de que deseas eliminar este juego?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        deleteGame(viewModel, idGame, navController)
                        showDialogDelete.value = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialogDelete.value = false // Cierra el diálogo sin hacer nada
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        val instant = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                        val localDate = instant.atZone(ZoneId.of("UTC")).toLocalDate()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val formattedDate = localDate.format(formatter)
                        editedRelaseDate = formattedDate
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            // The verticalScroll will allow scrolling to show the entire month in case there is not
            // enough horizontal space (for example, when in landscape mode).
            // Note that it's still currently recommended to use a DisplayMode.Input at the state in
            // those cases.
            DatePicker(
                state = datePickerState,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }

    if (showImageDialog) {
        FullScreenImageDialog(
            imageUrl = game!!.thumbnail!!,
            onDismiss = { showImageDialog = false }
        )
    }
}

@Composable
fun GameInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", color = Color.White, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, color = Color.LightGray, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 3.dp, start = 3.dp))
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            textColor = Color.White
        ),// Cambia el redondeado aquí
        singleLine = true,
    )
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun CustomTextFieldDate(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        enabled = false,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            textColor = Color.White,
            disabledTextColor = Color.White,
            disabledBorderColor = Color.White
        ),// Cambia el redondeado aquí
        singleLine = true,
    )
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun FullScreenImageDialog(imageUrl: String, onDismiss: () -> Unit) {
    // Variables de zoom y posición
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // Caja que cubre toda la pantalla
    Box(modifier = Modifier.fillMaxSize()
        .background(Color.Black)
    ) {

        Image(
            contentScale = ContentScale.FillWidth,
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale = min(max(scale * zoom, 1f), 5f) // Limitar el zoom entre 1x y 5x
                        offsetX += pan.x
                        offsetY += pan.y
                    }
                }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                )
                .fillMaxWidth()
                .align(Alignment.Center),
        )
        IconButton(onClick = { onDismiss.invoke() },
            modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth().padding(top = 100.dp)) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Cerrar",
                tint = Color.White,
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: String): String {
    val parsedDate = LocalDate.parse(date)
    return parsedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun deleteGame(viewModel: GamesViewModel, idGame: Int, navController: NavHostController) {
    viewModel.deleteGame(idGame)
    navController.previousBackStackEntry?.savedStateHandle?.set(
        DELETE_RESULT_KEY, "Videojuego eliminado exitosamente"
    )
    navController.popBackStack()
}