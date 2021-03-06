package app.ericn.mynews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ericn.android_common.StringProvider
import app.ericn.ericsweather.location.GetLocationUseCase
import app.ericn.mynews.core.NewsInteractor
import io.reactivex.Observable
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val interactor: NewsInteractor,
    private val getLocationUseCase: GetLocationUseCase,
    private val stringProvider: StringProvider,
    private val searchInputStream: Observable<String>
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(interactor, getLocationUseCase, stringProvider, searchInputStream) as T
    }

}