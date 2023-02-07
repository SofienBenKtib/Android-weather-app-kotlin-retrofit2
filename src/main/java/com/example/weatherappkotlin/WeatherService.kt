package com.example.weatherappkotlin

import android.telecom.Call
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path


interface WeatherService {

    @GET("?lat=35.7643&lon=10.813&appid=7664f1d0777e2d62d0aa1cc9b12de67e")
    fun getWeatherByCity(): retrofit2.Call<JsonObject>
}
