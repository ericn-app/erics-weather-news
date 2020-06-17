package app.ericn.mynews.core

import app.ericn.mynews.network.NewsApi
import app.ericn.mynews.network.NewsResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {
    fun fetchNews(cityName: String): Single<NewsResponse> =
        api.fetchNews(cityName).subscribeOn(Schedulers.io())
}