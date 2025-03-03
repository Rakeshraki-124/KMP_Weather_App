package org.example.project.data.apiService

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.models.WeatherResponse

class ApiServiece {

    private val httpClient = HttpClient{
        install(ContentNegotiation){
            json(
                Json{
                    encodeDefaults = true
                    isLenient = true
                    coerceInputValues = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getWeather(city: String): WeatherResponse{
        return httpClient.get("https://api.openweathermap.org/data/2.5/weather?"){
            parameter("q", city)
            parameter("appid", "c7ea4d00b09b9126e32a63b27d24a2d2")
        }.body()
    }
}

