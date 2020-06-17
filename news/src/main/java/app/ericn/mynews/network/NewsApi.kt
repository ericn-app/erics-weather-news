package app.ericn.mynews.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    fun fetchNews(@Query("q") query: String) : Single<NewsResponse>
}