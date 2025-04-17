package com.cstp2205.primenews.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.ui.screens.favourites.FavouritesScreen
import com.cstp2205.primenews.ui.screens.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    articles: List<Article>,
    favourites: List<Article>,
    onArticleClick: (Article) -> Unit,
    onSignOut: () -> Unit,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    var selectedTab by remember { mutableStateOf("headlines") }

    val topBarTitle = when (selectedTab) {
        "headlines" -> "News Headlines"
        "favourites" -> "Favourites"
        "profile" -> "Profile"
        else -> "News"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle, fontSize = 33.sp, fontWeight = FontWeight.Bold, color = Color(0xFF01579B)) },
                actions = {
                    Button(
                        onClick = onSignOut,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1976D2),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Sign Out")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Public, contentDescription = "Headlines") },
                    label = { Text("Headlines") },
                    selected = selectedTab == "headlines",
                    onClick = { selectedTab = "headlines" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF01579B),
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color(0xFF01579B),
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favourites") },
                    label = { Text("Favourites") },
                    selected = selectedTab == "favourites",
                    onClick = { selectedTab = "favourites" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF01579B),
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color(0xFF01579B),
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedTab == "profile",
                    onClick = { selectedTab = "profile" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF01579B),
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color(0xFF01579B),
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .padding(horizontal = 10.dp)
            ) {
                when (selectedTab) {
                    "headlines" -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            val categories =
                                listOf(null, "sports", "technology", "fashion", "health", "science")
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {
                                items(categories) { category ->
                                    val label =
                                        category?.replaceFirstChar { it.uppercase() } ?: "All"
                                    FilterChip(
                                        selected = (category == selectedCategory),
                                        onClick = { onCategorySelected(category) },
                                        label = { Text(label) },
                                        modifier = Modifier.padding(horizontal = 5.dp)
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))
                        }


                                items(articles) { article ->
                                    ArticleItem(article = article, onClick = { onArticleClick(article) }
                                    )

                                }
                        }

                    "favourites" -> FavouritesScreen(
                            favourites = favourites,
                            onArticleClick = onArticleClick,
                            onBack = { selectedTab = "headlines"}
                    )
                    "profile" -> ProfileScreen (
                        onLogout = onSignOut
                    )
                }
            }
        }
    )
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
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
