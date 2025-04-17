package com.cstp2205.primenews.data.repository

import com.cstp2205.primenews.data.local.ArticleDao
import com.cstp2205.primenews.data.local.FavouriteArticleDao
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.data.model.FavouriteArticle
import com.cstp2205.primenews.data.remote.NewsApiService

class NewsRepository(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao,
    private val favouriteArticleDao: FavouriteArticleDao
) {
    suspend fun fetchHeadlines(apiKey: String, category: String? = null): List<Article> {
        val response = apiService.getTopHeadlines(apiKey = apiKey, category = category)
        return response.articles.mapNotNull { dto ->
            dto.url?.let { url ->
                Article(
                    url = url,
                    author = dto.author,
                    content = dto.content,
                    description = dto.description,
                    publishedAt = dto.publishedAt,
                    title = dto.title,
                    urlToImage = dto.urlToImage
                )
            }
        }
    }

    suspend fun saveFavourite(article: Article, userId: String) {
        val fav = FavouriteArticle(
            url = article.url ?: "",
            title = article.title,
            description = article.description,
            content = article.content,
            author = article.author,
            publishedAt = article.publishedAt,
            urlToImage = article.urlToImage,
            userId = userId
        )
        favouriteArticleDao.insertFavourite(fav)
    }

    suspend fun getFavourites(userId: String): List<Article> {
        return favouriteArticleDao.getFavouriteArticles(userId).map { fav ->
            Article(
                url = fav.url,
                title = fav.title,
                description = fav.description,
                author = fav.author,
                publishedAt = fav.publishedAt,
                content = fav.content,
                urlToImage = fav.urlToImage
            )
        }
    }
}
