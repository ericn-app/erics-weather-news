package app.ericn.ericsweather.weather.core

import app.ericn.ericsweather.weather.network.CurrentWeatherResponse
import app.ericn.ericsweather.weather.network.WeatherApi
import app.ericn.ericsweather.weather.network.WeatherForecastResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    fun fetchCurrent(cityName: String): Single<CurrentWeatherResponse> {
        return api.getWeather(cityName).subscribeOn(Schedulers.io())
    }

    fun fetchForecast(cityName: String) : Single<WeatherForecastResponse> {
        return api.getForecast(cityName).subscribeOn(Schedulers.io())
    }
}