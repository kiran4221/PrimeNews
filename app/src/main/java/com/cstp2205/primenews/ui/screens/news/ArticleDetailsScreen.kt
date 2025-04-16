package com.cstp2205.primenews.ui.screens.news

//Necessary imports for making the URLs working

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Details", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF01579B)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    onSaveFavourite(article)
                    Toast.makeText(context, "Article added to favourites", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Save to Favourites")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = article.title ?: "No Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(15.dp))
                if (article.urlToImage != null) {
                    Image(
                        painter = rememberAsyncImagePainter(article.urlToImage),
                        contentDescription = "Article Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = article.urlToImage ?: "No url available",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        // Safely check for a valid URL
                        article.url?.let { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = article.author ?: "Author Name not given",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = article.publishedAt ?: "No info",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = article.description ?: "Description is not available",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = article.content ?: "Content not available",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("To read full article, click the url below...")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = article.url ?: "No url available",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        // Safely check for a valid URL
                        article.url?.let { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}
