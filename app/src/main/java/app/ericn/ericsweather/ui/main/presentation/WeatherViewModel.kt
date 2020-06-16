package app.ericn.ericsweather.ui.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.ericn.ericsweather.R
import app.ericn.ericsweather.StringProvider
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class WeatherViewModel(
    interactor: CurrentWeatherInteractor,
    stringProvider: StringProvider,
    searchSubject: PublishSubject<String>
) :
    ViewModel() {
    private val viewState = MutableLiveData<ViewState>()
    val viewStateReadOnly: LiveData<ViewState> = viewState
    private val disposables = CompositeDisposable()

    init {
        searchSubject.observeOn(Schedulers.io()).doOnNext {
            println("search query is $it")
        }.flatMapSingle { cityName ->
            interactor(cityName)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                viewState.value =
                    ViewState.DataLoaded(
                        CurrentWeatherUI(
                            cityName = result.cityName,
                            maxMin = stringProvider.getString(
                                R.string.max_min,
                                result.maxTemp.toString(),
                                result.minTemp.toString()
                            ),
                            currentTemp = stringProvider.getString(
                                R.string.current,
                                result.currentTemp
                            ),
                            symbol = "https://openweathermap.org/img/w/${result.symbol}.png"
                        )
                    )
            }, { t ->
                viewState.value =
                    ViewState.Error(
                        "Sorry something went wrong"
                    )
            }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    sealed class ViewState {
        data class DataLoaded(val currentWeather: CurrentWeatherUI) : ViewState()
        object Loading : ViewState()
        data class Error(val message: String) : ViewState()
    }
}