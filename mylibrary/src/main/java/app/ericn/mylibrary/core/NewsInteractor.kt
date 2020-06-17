package app.ericn.ericsweather.core

import io.reactivex.Single
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(cityName: String): Single<List<Article>> {
        return repository.fetchNews(cityName).map {
            it.articles.map { article ->
                Article(article.title, article.source.name, article.urlToImage)
            }
        }
    }
}