package com.cstp2205.primenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import com.cstp2205.primenews.ui.screens.auth.SignInScreen
import com.cstp2205.primenews.ui.screens.auth.SignUpScreen
import com.cstp2205.primenews.ui.screens.news.NewsScreen
import com.cstp2205.primenews.ui.theme.PrimeNewsTheme
import com.google.firebase.auth.ActionCodeUrl
import com.cstp2205.primenews.viewmodel.NewsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cstp2205.primenews.data.model.Article
import com.cstp2205.primenews.ui.screens.favourites.FavouritesScreen
import com.cstp2205.primenews.ui.screens.profile.ProfileScreen
import com.cstp2205.primenews.ui.screens.news.ArticleDetailScreen
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeNewsTheme {
                PrimeNewsApp()
            }
        }
    }
}

@Composable
fun PrimeNewsApp() {
    var currentScreen by remember { mutableStateOf("signIn") }
    val newsViewModel: NewsViewModel = viewModel()
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    when (currentScreen) {
        "signIn" -> SignInScreen(
            onSignInSuccess = {
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                newsViewModel.loadFavourites(currentUserId)
                currentScreen = "newsScreen" },
            onNavigateToSignUp = { currentScreen = "signUp" }
        )
        "signUp" -> SignUpScreen(
            onSignUpSuccess = { currentScreen = "newsScreen" },
            onNewsScreen = {currentScreen = "newsScreen"},
            onNavigateToSignIn = { currentScreen = "signIn" }
        )
        "newsScreen" -> {
            LaunchedEffect(Unit) {
                newsViewModel.loadNews("8bb8e5a543b4418a807e4e69b3c4af4a")
            }
            NewsScreen(
                articles = newsViewModel.articles,
                favourites = newsViewModel.favourites,
                onArticleClick = { article ->
                    selectedArticle = article
                    currentScreen = "detailScreen"
                },
                onSignOut = { currentScreen = "signIn" },
                selectedCategory = newsViewModel.selectedCategory,
                onCategorySelected = {category -> newsViewModel.chooseCategory(category)}
            )
        }
        "detailScreen" -> {
            selectedArticle?.let { article ->
                ArticleDetailScreen(
                    article = article,
                    onSaveFavourite = {
                        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                        newsViewModel.saveFavourite(article, currentUserId)
                        currentScreen = "newsScreen" },
                    onBack = { currentScreen = "newsScreen" }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrimeNewsTheme {
        PrimeNewsApp()
    }
}
