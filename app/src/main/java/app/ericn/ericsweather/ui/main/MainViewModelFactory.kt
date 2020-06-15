package app.ericn.ericsweather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor

class MainViewModelFactory(private val interactor: CurrentWeatherInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(interactor) as T
    }

}