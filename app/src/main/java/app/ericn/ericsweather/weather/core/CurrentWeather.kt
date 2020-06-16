package app.ericn.ericsweather.weather.core

data class CurrentWeather(
    val cityName: String,
    val maxTemp: Int,
    val minTemp: Int,
    val currentTemp: Int,
    val symbol: String
)