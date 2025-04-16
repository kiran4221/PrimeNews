package com.cstp2205.primenews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_articles")
data class FavouriteArticle(
    @PrimaryKey val url: String,
    val title: String?,
    val description: String?,
    val content: String?,
    val author: String?,
    val publishedAt: String?,
    val urlToImage: String?,
    // Added a mew field below  to store articles based on current user
    val userId: String?
)
