package app.ericn.ericsweather

import app.ericn.ericsweather.weather.core.WeatherForecastInteractor
import app.ericn.ericsweather.weather.core.WeatherRepository
import app.ericn.ericsweather.weather.network.WeatherForecastResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherForecastInteractorTest {
    @Mock
    lateinit var repository: WeatherRepository

    private lateinit var underTest: WeatherForecastInteractor

    @Before
    fun setup() {
        underTest = WeatherForecastInteractor(repository)
    }

    @Test
    fun success() {
        val weather = WeatherForecastResponse.Forecast.Weather("01n")
        val forecast = WeatherForecastResponse.Forecast(
            WeatherForecastResponse.Forecast.Main(25.0, 15.0),
            listOf(weather)
        )
        val forecasts = (0..40).map { _ -> forecast }.toList()
        val fakeReponse = WeatherForecastResponse(forecasts)
        whenever(repository.fetchForecast(any())).thenReturn(Single.just(fakeReponse))

        val testObserver = underTest.invoke("london").test()

        testObserver.assertComplete().assertNoErrors().assertValue {
            it.size == 3 && it[0].maxTemp == 25 && it[0].minTemp == 15 && it[0].symbol == "https://openweathermap.org/img/w/01n.png"
        }
    }
}