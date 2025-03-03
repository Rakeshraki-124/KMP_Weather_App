package org.example.project


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*

import org.jetbrains.compose.ui.tooling.preview.Preview




@Composable
@Preview
fun App() {
    MaterialTheme {

        HomeScreenUI()


//        val viewModel = remember { HomeScreenViewModel() }
//        viewModel.getWeather(city = "Dhenkanal")
//        val state = viewModel.state.collectAsState()
//        when(state.value){
//            is HomeScreenState.Error -> {
//                Text("Error")
//            }
//            HomeScreenState.Loading ->{
//                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
//                    CircularProgressIndicator()
//                }
//            }
//            is HomeScreenState.Success -> {
//                val weather = (state.value as HomeScreenState.Success).weatherResponse
//
//                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(weather.name.toString())
//                    Text(weather.main?.temp.toString())
//                    Text(weather.weather?.get(0)?.description.toString())
//                }
//            }
//        }
    }
}