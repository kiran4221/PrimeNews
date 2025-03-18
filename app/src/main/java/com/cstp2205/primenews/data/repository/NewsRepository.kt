package com.cstp2205.primenews.data.repository

import com.cstp2205.primenews.data.local.ArticleDao
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.data.remote.NewsApiService

class NewsRepository(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao
) {
    suspend fun fetchTeslaNews(apiKey: String): List<Article> {
        val response = apiService.getTeslaNews(apiKey = apiKey)
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

    suspend fun saveArticle(article: Article) {
        articleDao.insertArticle(article)
    }

    suspend fun getSavedArticles(): List<Article> = articleDao.getAllArticles()

    suspend fun removeArticle(articleUrl: String) {
        articleDao.deleteArticleByUrl(articleUrl)
    }
}
