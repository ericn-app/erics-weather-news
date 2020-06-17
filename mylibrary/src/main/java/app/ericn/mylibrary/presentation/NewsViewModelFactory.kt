package app.ericn.mylibrary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ericn.android_common.StringProvider
import app.ericn.ericsweather.core.NewsInteractor
import app.ericn.ericsweather.ui.news.NewsViewModel
import io.reactivex.Observable
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val interactor: NewsInteractor,
    private val stringProvider: StringProvider,
    private val searchInputStream: Observable<String>
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(interactor, stringProvider, searchInputStream) as T
    }

}