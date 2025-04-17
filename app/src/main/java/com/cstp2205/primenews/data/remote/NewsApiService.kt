package com.cstp2205.primenews.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.cstp2205.primenews.data.model.NewsResponse

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String? = null,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
