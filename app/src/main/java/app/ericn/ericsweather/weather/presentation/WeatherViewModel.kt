package app.ericn.ericsweather.weather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.ericn.android_common.StringProvider
import app.ericn.ericsweather.R
import app.ericn.ericsweather.weather.core.CurrentWeatherInteractor
import app.ericn.ericsweather.weather.core.WeatherForecastInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(
    currentInteractor: CurrentWeatherInteractor,
    forecastInteractor: WeatherForecastInteractor,
    stringProvider: StringProvider,
    searchSubject: Observable<String>
) :
    ViewModel() {
    private val viewState = MutableLiveData<ViewState>()
    val viewStateReadOnly: LiveData<ViewState> = viewState
    private val disposables = CompositeDisposable()

    init {
        searchSubject
            .observeOn(Schedulers.io())
            .flatMapSingle { cityName ->
                Singles.zip(currentInteractor(cityName), forecastInteractor(cityName))
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val currentWeather = result.first
                val tomorrow = result.second[0]
                val dayAfter = result.second[1]
                val twoDaysLater = result.second[2]
                viewState.value =
                    ViewState.DataLoaded(
                        currentWeather = CurrentWeatherUI(
                            cityName = currentWeather.cityName,
                            maxMin = stringProvider.getString(
                                R.string.max_min_long,
                                currentWeather.maxTemp.toString(),
                                currentWeather.minTemp.toString()
                            ),
                            currentTemp = stringProvider.getString(
                                R.string.current,
                                currentWeather.currentTemp
                            ),
                            symbol = currentWeather.symbol
                        ),
                        tomorrow = WeatherForecastUI(
                            maxMin = stringProvider.getString(
                                R.string.max_min,
                                tomorrow.maxTemp,
                                tomorrow.minTemp
                            ),
                            symbol = tomorrow.symbol
                        ),
                        dayAfter = WeatherForecastUI(
                            maxMin = stringProvider.getString(
                                R.string.max_min,
                                dayAfter.maxTemp,
                                dayAfter.minTemp
                            ),
                            symbol = dayAfter.symbol
                        ),
                        twoDaysLater = WeatherForecastUI(
                            maxMin = stringProvider.getString(
                                R.string.max_min,
                                twoDaysLater.maxTemp,
                                twoDaysLater.minTemp
                            ),
                            symbol = twoDaysLater.symbol
                        )
                    )
            }, { t ->
                viewState.value =
                    ViewState.Error(t.message?: stringProvider.getString(R.string.error_generic))
            }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    sealed class ViewState {
        data class DataLoaded(
            val currentWeather: CurrentWeatherUI,
            val tomorrow: WeatherForecastUI,
            val dayAfter: WeatherForecastUI,
            val twoDaysLater: WeatherForecastUI
        ) : ViewState()

        object Loading : ViewState()
        data class Error(val message: String) : ViewState()
    }
}