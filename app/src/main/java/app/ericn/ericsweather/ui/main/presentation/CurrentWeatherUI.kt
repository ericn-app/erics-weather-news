package app.ericn.ericsweather.ui.main.presentation

data class CurrentWeatherUI(
    val cityName: String,
    val maxMin: String,
    val currentTemp: String,
    val symbol: String
)