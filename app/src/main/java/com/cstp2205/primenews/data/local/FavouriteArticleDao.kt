package com.cstp2205.primenews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cstp2205.primenews.data.model.FavouriteArticle

@Dao
interface FavouriteArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(article: FavouriteArticle)

    // Updated get favourites query to fetch articles for current users
    @Query("SELECT * FROM favourite_articles WHERE userId = :userId")
    suspend fun getFavouriteArticles(userId: String): List<FavouriteArticle>
}