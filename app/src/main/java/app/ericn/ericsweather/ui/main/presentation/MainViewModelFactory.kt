package app.ericn.ericsweather.ui.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.StringProvider
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor
import io.reactivex.subjects.PublishSubject

class MainViewModelFactory(
    private val interactor: CurrentWeatherInteractor,
    private val stringProvider: StringProvider,
    private val searchSubject: PublishSubject<String>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            interactor,
            stringProvider,
            searchSubject
        ) as T
    }

}