package app.ericn.ericsweather.ui.main

import androidx.lifecycle.ViewModel
import app.ericn.ericsweather.ui.main.core.CurrentWeatherInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class MainViewModel(interactor: CurrentWeatherInteractor) : ViewModel() {
    private val disposables = CompositeDisposable()
    init {
        interactor.invoke().observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
            println(result)
        }, { t ->
            println(t)
        }).addTo(disposables)
    }
}