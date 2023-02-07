package com.example.weatherappkotlin


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var cityName: EditText
    lateinit var searchButton: Button
    lateinit var weatherImage: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCityName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityName = findViewById(R.id.cityName)
        searchButton = findViewById(R.id.search)
        weatherImage = findViewById(R.id.weatherImage)
        tvTemperature = findViewById(R.id.temperature)
        tvCityName = findViewById(R.id.tvCityName)
        //TODO: Create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val weatherService = retrofit.create(WeatherService::class.java)
        //TODO: Call weather api

        val result = weatherService.getWeatherByCity()
        result.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Server error, please try again later",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}