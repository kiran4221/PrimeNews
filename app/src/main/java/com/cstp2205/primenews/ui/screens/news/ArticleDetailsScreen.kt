package com.cstp2205.primenews.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cstp2205.primenews.data.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    article: Article,
    onSaveFavourite: (Article) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Details", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF01579B)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = article.title ?: "No Title", fontWeight = FontWeight.Bold, fontSize = 25.sp, style = MaterialTheme.typography.titleLarge,)
                    Spacer(modifier = Modifier.height(15.dp))
                    if (article.urlToImage != null) {
                        Image(
                            painter = rememberAsyncImagePainter(article.urlToImage),
                            contentDescription = "Article Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .aspectRatio(16f/9f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = article.urlToImage ?: "No url available", style = MaterialTheme.typography.bodySmall, color = Color.Blue)

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(text = article.author ?: "Author Name not given", style = MaterialTheme.typography.displaySmall)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = article.publishedAt ?: "No info", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(12.dp))

                    /*Text(text = article.content ?: "No Content Available", style = MaterialTheme.typography.titleSmall)

                    Spacer(modifier = Modifier.height(10.dp))*/

                    Text(text = article.description ?: "Description is not available", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "To read full article, click the url below...")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = article.url ?: "No url available", style = MaterialTheme.typography.bodySmall, color = Color.Blue)

                }
                Button(
                    onClick = { onSaveFavourite(article) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save to Favourites")
                }
            }
        }
    )
}
