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
    lateinit var layoutWeather: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityName = findViewById(R.id.cityName)
        searchButton = findViewById(R.id.search)
        weatherImage = findViewById(R.id.weatherImage)
        tvTemperature = findViewById(R.id.temperature)
        tvCityName = findViewById(R.id.tvCityName)
        layoutWeather = findViewById(R.id.weatherLayout)
        searchButton.setOnClickListener {
            val city = cityName.text.toString()
            if (city.isEmpty()) {
                Toast.makeText(this, "City name can't be empty!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Server error, please try again later",Toast.LENGTH_SHORT).show()
                //TODO:Fix this later
                //getWeatherByCity(city)
            }
        }


        //TODO: Create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherService = retrofit.create(WeatherService::class.java)

        //TODO: Call weather api
        val result = weatherService.getWeatherByCity(city = "Paris")
        result.enqueue(
            object : Callback<WeatherResult> {
                override fun onResponse(
                    call: Call<WeatherResult>,
                    response: Response<WeatherResult>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Picasso.get()
                            .load("https://openweathermap.org/img/w/${result?.weather?.get(0)?.icon}.png")
                            .into(weatherImage)
                        tvTemperature.text = "${result?.main?.temp} °C"
                        tvCityName.text = "${result?.name}"
                        layoutWeather.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Server error, please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }
}