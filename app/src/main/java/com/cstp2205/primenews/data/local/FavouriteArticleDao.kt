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

    @Query("SELECT * FROM favourite_articles")
    suspend fun getFavouriteArticles(): List<FavouriteArticle>
}