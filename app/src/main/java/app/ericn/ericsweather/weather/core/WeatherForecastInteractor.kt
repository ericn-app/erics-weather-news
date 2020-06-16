package app.ericn.ericsweather.weather.core

import io.reactivex.Single
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherForecastInteractor @Inject constructor(private val repository: WeatherRepository) {
    operator fun invoke(cityName: String): Single<List<WeatherForecast>> {
        return repository.fetchForecast(cityName).map { response ->
            // TODO log to Crashlytics if there are fewer than 5 days in response i.e. list.size<40
            // Sampling the weather data using mean, may or may not be the optimal UX, needs discussion
            response.list.filterIndexed { index, _ -> index % 8 == 4 }.map { forecast ->
                WeatherForecast(
                    maxTemp = forecast.main.tempMax.roundToInt(),
                    minTemp = forecast.main.tempMin.roundToInt(),
                    symbol = "https://openweathermap.org/img/w/${forecast.weather[0].icon}.png"
                )
            }.take(3)
        }
    }
}