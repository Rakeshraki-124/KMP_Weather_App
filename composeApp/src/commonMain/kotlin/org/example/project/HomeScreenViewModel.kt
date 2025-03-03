package org.example.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.models.WeatherResponse

class HomeScreenViewModel : ViewModel() {
    private val repository = WeatherRepository()

    var _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    var state = _state.asStateFlow()

     fun getWeather(city: String){
        viewModelScope.launch{
            _state.value = HomeScreenState.Loading
            try {
                val weatherResponse = repository.getWeather(city = city)
                _state.value = HomeScreenState.Success(weatherResponse)
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error(e.message?: "Something went wrong")
            }
        }
    }
}

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val weatherResponse: WeatherResponse) : HomeScreenState()
    data class Error(val throwable: String) : HomeScreenState()
}