package com.example.weatherappkotlin

import android.telecom.Call
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherService {
    companion object{
      const val API_KEY = "7664f1d0777e2d62d0aa1cc9b12de67e"
    }

    @GET("?q=lat=35.7643&lon=10.813&appid=$API_KEY")
    fun getWeatherByCity(@Query("q") city: String): retrofit2.Call<WeatherResult>
}
