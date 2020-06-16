package app.ericn.ericsweather.ui.main.core

import io.reactivex.Single
import kotlin.math.roundToInt

class CurrentWeatherInteractor(private val repository: WeatherRepository) {
    operator fun invoke(cityName: String): Single<CurrentWeather> {
        return repository.fetchCurrent(cityName).map {
            CurrentWeather(
                cityName = it.name,
                maxTemp = it.main.tempMax.roundToInt(),
                minTemp = it.main.tempMin.roundToInt(),
                currentTemp = it.main.temp.roundToInt(),
                symbol = "https://openweathermap.org/img/w/${it.weather[0].icon}.png"
            )
        }
    }
}