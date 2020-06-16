package app.ericn.ericsnews.core

import app.ericn.ericsnews.network.NewsApi
import app.ericn.ericsnews.network.NewsResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NewsRepository(private val api: NewsApi) {
    fun fetchNews(cityName: String): Single<NewsResponse> =
        api.fetchNews(cityName).subscribeOn(Schedulers.io())
}