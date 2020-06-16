package app.ericn.ericsweather.weather.presentation

data class CurrentWeatherUI(
    val cityName: String,
    val maxMin: String,
    val currentTemp: String,
    val symbol: String
)