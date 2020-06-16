package app.ericn.ericsweather.weather.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    fun getWeather(@Query("q") cityName: String): Single<CurrentWeatherResponse>

    @GET("data/2.5/forecast")
    fun getForecast(@Query("q") cityName: String): Single<WeatherForecastResponse>
}