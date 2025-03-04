package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear

@Composable
fun HomeScreenUITest() {
    val viewModel = remember { HomeScreenViewModel() }
    val state = viewModel.state.collectAsState()
    var cityName = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // City Name Input
        OutlinedTextField(
            value = cityName.value,
            onValueChange = { cityName.value = it },
            label = { Text("Enter City Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (cityName.value.isNotEmpty()) {
                    IconButton(onClick = { cityName.value = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fetch Weather Button
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.getWeather(city = cityName.value)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = cityName.value.isNotEmpty()
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Weather Data Display with Animation
        Crossfade(
            targetState = state.value,
            animationSpec = tween(durationMillis = 300)
        ) { currentState ->
            when (currentState) {
                is HomeScreenState.Error -> {
                    val errorMessage = (currentState as HomeScreenState.Error)
                    Text(
                        text = "Error: $errorMessage",
                        color = MaterialTheme.colors.error,
                        fontSize = 18.sp
                    )
                }
                HomeScreenState.Loading -> {
                    CircularProgressIndicator()
                }
                is HomeScreenState.Success -> {
                    val weather = (currentState as HomeScreenState.Success).weatherResponse

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "City: ${weather.name}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Temperature: ${weather.main?.temp}°C",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Humidity: ${weather.main?.humidity}%",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Pressure: ${weather.main?.pressure} hPa",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Description: ${weather.weather?.get(0)?.description}",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}