package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun HomeScreenUI() {

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
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Fetch Weather Button
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.getWeather(city = cityName.value)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get Weather")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Weather Data Display
                when (state.value) {
                    is HomeScreenState.Error -> {
                        Text(
                            text = "Error fetching weather data",
                            color = MaterialTheme.colors.error,
                            fontSize = 18.sp
                        )
                    }
                    HomeScreenState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is HomeScreenState.Success -> {
                        val weather = (state.value as HomeScreenState.Success).weatherResponse

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = weather.name.toString(),
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
                                text = "Description: ${weather.weather?.get(0)?.description}",
                                fontSize = 18.sp
                            )
                        }
                    }
                }

    }
}