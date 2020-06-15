package app.ericn.ericsweather.ui.main.core

import app.ericn.ericsweather.ui.main.network.CurrentWeatherResponse
import io.reactivex.Single

class CurrentWeatherInteractor(private val repository: WeatherRepository) {
    operator fun invoke(): Single<CurrentWeatherResponse> {
        return repository.fetchCurrent()
    }
}