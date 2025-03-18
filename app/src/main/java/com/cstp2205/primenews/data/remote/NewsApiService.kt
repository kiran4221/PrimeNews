package com.cstp2205.primenews.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.cstp2205.primenews.data.model.NewsResponse

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getTeslaNews(
        @Query("q") query: String = "tesla",
        @Query("from") from: String = "2025-02-18",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
