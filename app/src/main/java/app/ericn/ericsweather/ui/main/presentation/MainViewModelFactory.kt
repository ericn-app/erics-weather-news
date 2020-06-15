package app.ericn.ericsweather.ui.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.StringProvider
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor

class MainViewModelFactory(private val interactor: CurrentWeatherInteractor, private val stringProvider: StringProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            interactor,
            stringProvider
        ) as T
    }

}