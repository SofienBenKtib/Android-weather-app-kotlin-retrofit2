package com.example.weatherappkotlin

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface WeatherService {

    @GET("?lat=44.34&lon=10.99&appid=7664f1d0777e2d62d0aa1cc9b12de67e")
    fun getWeatherByCity(): retrofit2.Call<String>
}
