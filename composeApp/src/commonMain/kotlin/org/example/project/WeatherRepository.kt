package org.example.project

import org.example.project.data.apiService.ApiServiece

class WeatherRepository {
    private val apiServiece = ApiServiece()
    suspend fun getWeather(city: String) = apiServiece.getWeather(city=city)
}