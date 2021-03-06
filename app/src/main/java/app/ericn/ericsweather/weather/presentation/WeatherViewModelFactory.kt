package app.ericn.ericsweather.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ericn.android_common.StringProvider
import app.ericn.ericsweather.location.GetLocationUseCase
import app.ericn.ericsweather.location.LocationPermissionsHelper
import app.ericn.ericsweather.weather.core.CurrentWeatherInteractor
import app.ericn.ericsweather.weather.core.WeatherForecastInteractor
import io.reactivex.Observable
import javax.inject.Inject

class WeatherViewModelFactory @Inject constructor(
    private val currentInteractor: CurrentWeatherInteractor,
    private val forecastInteractor: WeatherForecastInteractor,
    private val locationUseCase: GetLocationUseCase,
    private val permissionsHelper: LocationPermissionsHelper,
    private val stringProvider: StringProvider,
    private val searchSubject: Observable<String>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            currentInteractor,
            forecastInteractor,
            locationUseCase,
            permissionsHelper,
            stringProvider,
            searchSubject
        ) as T
    }

}