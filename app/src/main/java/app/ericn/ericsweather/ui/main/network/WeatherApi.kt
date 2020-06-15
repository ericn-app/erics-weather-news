package app.ericn.ericsweather.ui.main.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    fun fetchOrdersObservable(@Query("q") cityName: String): Single<CurrentWeatherResponse>
}