package app.ericn.ericsweather.weather.core

import io.reactivex.Single
import javax.inject.Inject
import kotlin.math.roundToInt

class CurrentWeatherInteractor @Inject constructor(private val repository: WeatherRepository) {
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