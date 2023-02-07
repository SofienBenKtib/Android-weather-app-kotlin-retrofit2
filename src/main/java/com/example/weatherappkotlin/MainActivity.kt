package com.example.weatherappkotlin


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var cityName: EditText
    lateinit var searchButton: Button
    lateinit var weatherImage: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCityName: TextView
    lateinit var layoutWeather:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityName = findViewById(R.id.cityName)
        searchButton = findViewById(R.id.search)
        weatherImage = findViewById(R.id.weatherImage)
        tvTemperature = findViewById(R.id.temperature)
        tvCityName = findViewById(R.id.tvCityName)
        layoutWeather=findViewById(R.id.weatherLayout)
        //TODO: Create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherService = retrofit.create(WeatherService::class.java)

        //TODO: Call weather api
        val result = weatherService.getWeatherByCity()
        result.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val main = result?.get("main")?.asJsonObject
                    val temp = main?.get("temp")?.asDouble
                    val cityName = result?.get("name")?.asString
                    val weather = result?.get("weather")?.asJsonArray
                    val icon = weather?.get(0)?.asJsonObject?.get("icon")?.asString
                    Picasso.get().load("https://openweathermap.org/img/w/$icon.png")
                        .into(weatherImage)
                    tvTemperature.text = "$temp °C"
                    tvCityName.text = "$cityName"
                    layoutWeather.visibility= View.VISIBLE
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Server error, please try again later",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}