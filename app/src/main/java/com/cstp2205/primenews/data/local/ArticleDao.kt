package com.cstp2205.primenews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cstp2205.primenews.data.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("DELETE FROM articles WHERE url = :articleUrl")
    suspend fun deleteArticleByUrl(articleUrl: String)
}