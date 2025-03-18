package com.cstp2205.primenews.ui.screens.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cstp2205.primenews.data.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onSignOut: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Headlines", fontSize = 20.sp) },
                actions = {
                    TextButton(onClick = onSignOut) {
                        Text("Sign Out")
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(articles) { article ->
                        ArticleItem(article = article, onClick = { onArticleClick(article) })
                    }
                }
            }
        }
    )
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = article.title ?: "No Title", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description ?: "No Description", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
