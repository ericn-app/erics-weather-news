package app.ericn.ericsnews.core

import io.reactivex.Single

class NewsInteractor(private val repository: NewsRepository) {
    operator fun invoke(cityName: String): Single<List<Article>> {
        return repository.fetchNews(cityName).map {
            it.articles.map { article ->
                Article(article.title, article.source.name, article.urlToImage)
            }
        }
    }
}