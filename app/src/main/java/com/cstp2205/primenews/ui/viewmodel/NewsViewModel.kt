package com.cstp2205.primenews.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.data.repository.NewsRepository
import com.cstp2205.primenews.data.remote.NewsApiService
import com.cstp2205.primenews.data.local.AppDatabase
import com.cstp2205.primenews.data.local.FavouriteArticleDao
import com.cstp2205.primenews.data.local.ArticleDao
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(NewsApiService::class.java)
    private val repository = NewsRepository(apiService,db.articleDao(), db.favouriteArticleDao())

    val articles = mutableStateListOf<Article>()
    val favourites = mutableStateListOf<Article>()
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadNews(apiKey: String) {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val newsList = repository.fetchHeadlines(apiKey)
                articles.clear()
                articles.addAll(newsList)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun saveFavourite(article: Article) {
        viewModelScope.launch {
            repository.saveFavourite(article)
            favourites.clear()
            favourites.addAll(repository.getFavourites())
        }
    }

    fun loadFavourites() {
        viewModelScope.launch {
            favourites.clear()
            favourites.addAll(repository.getFavourites())
        }
    }
}