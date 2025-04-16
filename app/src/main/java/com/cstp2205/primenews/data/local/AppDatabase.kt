package com.cstp2205.primenews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.data.model.FavouriteArticle

@Database(entities = [Article::class, FavouriteArticle::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favouriteArticleDao(): FavouriteArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adding the new column 'userId' to the favourite_articles table.
                // The column is declared as TEXT and NOT NULL, so we set a default value.
                database.execSQL("ALTER TABLE favourite_articles ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getInstance(context: Context): AppDatabase =
                    INSTANCE ?: synchronized(this) {
                        INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                    }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "prime_news.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }
}