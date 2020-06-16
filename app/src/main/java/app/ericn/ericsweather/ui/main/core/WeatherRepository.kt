package app.ericn.ericsweather.ui.main.core

import app.ericn.ericsweather.ui.main.network.CurrentWeatherResponse
import app.ericn.ericsweather.ui.main.network.WeatherApi
import app.ericn.ericsweather.ui.main.network.WeatherForecastResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class WeatherRepository(private val api: WeatherApi) {
    fun fetchCurrent(cityName: String): Single<CurrentWeatherResponse> {
        return api.getWeather(cityName).subscribeOn(Schedulers.io())
    }

    fun fetchForecast(cityName: String) : Single<WeatherForecastResponse> {
        return api.getForecast(cityName).subscribeOn(Schedulers.io())
    }
}