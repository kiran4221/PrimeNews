package com.cstp2205.primenews.ui.screens.favourites

import android.R.attr.content
import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cstp2205.primenews.data.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    favourites: List<Article>,
    onArticleClick: (Article) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(favourites) { article ->
                    Card(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onArticleClick(article) },
                        colors = CardDefaults.cardColors(
                            contentColor = Color.Black,
                            containerColor = Color(0xFFE3F2FD)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = article.title ?: "No Title",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = article.description ?: "No Description", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    )
}
