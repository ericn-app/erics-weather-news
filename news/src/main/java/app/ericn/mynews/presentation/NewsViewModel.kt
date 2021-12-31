package app.ericn.mynews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.ericn.android_common.StringProvider
import app.ericn.ericsweather.location.GetLocationUseCase
import app.ericn.mynews.R
import app.ericn.mynews.core.Article
import app.ericn.mynews.core.NewsInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val interactor: NewsInteractor,
    getLocationUseCase: GetLocationUseCase,
    private val stringProvider: StringProvider,
    searchInputStream: Observable<String>
) : ViewModel() {

    private val viewState = MutableLiveData<ViewState>()
    val viewStateReadOnly: LiveData<ViewState> = viewState
    private val disposables = CompositeDisposable()

    init {
        handleCityNameStream(getLocationUseCase().map { it.cityName }.toObservable())
        handleCityNameStream(searchInputStream)
    }

    private fun handleCityNameStream(handleCityNameStream: Observable<String>) {
        handleCityNameStream
            .subscribeOn(Schedulers.io()).flatMapSingle { cityName ->
                interactor(cityName)
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                viewState.value = ViewState.DataLoaded(articles)
            }, { t ->
                viewState.value =
                    ViewState.Error(t.message ?: stringProvider.getString(R.string.error_generic))
            }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    sealed class ViewState {
        data class DataLoaded(val articles: List<Article>) : ViewState()

        object Loading : ViewState()
        data class Error(val message: String) : ViewState()
    }
}
