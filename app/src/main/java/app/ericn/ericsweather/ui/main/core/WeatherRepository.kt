package app.ericn.ericsweather.ui.main.core

import app.ericn.ericsweather.ui.main.network.CurrentWeatherResponse
import app.ericn.ericsweather.ui.main.network.WeatherApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class WeatherRepository(private val api: WeatherApi) {
    fun fetchCurrent(cityName: String): Single<CurrentWeatherResponse> {
        return api.fetchOrdersObservable(cityName).subscribeOn(Schedulers.io())
    }
}