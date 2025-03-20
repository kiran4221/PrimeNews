package com.cstp2205.primenews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.data.model.FavouriteArticle

@Database(entities = [Article::class, FavouriteArticle::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favouriteArticleDao(): FavouriteArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "prime_news_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}