package app.ericn.ericsweather.weather.network


import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list")
    val list: List<Forecast>
) {
    data class Forecast(
        @SerializedName("main")
        val main: Main,
        @SerializedName("weather")
        val weather: List<Weather>
    ) {
        data class Main(
            @SerializedName("temp_max")
            val tempMax: Double,
            @SerializedName("temp_min")
            val tempMin: Double
        )

        data class Weather(
            @SerializedName("icon")
            val icon: String
        )
    }
}