package app.ericn.ericsweather.ui.main.core

data class CurrentWeather(
    val cityName: String,
    val maxTemp: Int,
    val minTemp: Int,
    val currentTemp: Int,
    val symbol: String
)