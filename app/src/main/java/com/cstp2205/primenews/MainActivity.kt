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
    var dashboardTab by remember { mutableStateOf("news") }
    val newsViewModel: NewsViewModel = viewModel()
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    when (currentScreen) {
        "signIn" -> SignInScreen(
            onSignInSuccess = { currentScreen = "newsScreen" },
            onNavigateToSignUp = { currentScreen = "signUp" }
        )
        "signUp" -> SignUpScreen(
            onSignUpSuccess = { currentScreen = "newsScreen" },
            onNavigateToSignIn = { currentScreen = "signIn" }
        )
        "newsScreen" -> {
            LaunchedEffect(Unit) {
                newsViewModel.loadNews("8bb8e5a543b4418a807e4e69b3c4af4a")
            }
            Scaffold (
                bottomBar = {
                    BottomNavBar(
                        currentTab = dashboardTab,
                        onTabSelected = { dashboardTab = it}
                    )
                }
            ) {
                innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    when (dashboardTab) {
                        "news" -> NewsScreen(
                            articles = newsViewModel.articles,
                            onArticleClick = {article ->
                                selectedArticle = article
                                currentScreen = "detailScreen"
                            },
                            onSignOut = { currentScreen = "signIn"}
                        )
                        "favourites" -> FavouritesScreen(
                            favourites = newsViewModel.favourites,
                            onArticleClick = { article ->
                                selectedArticle = article
                                currentScreen = "detailScreen"
                            },
                            onBack = {dashboardTab = "news"}
                        )
                        "profile" -> ProfileScreen(
                            onLogout = { currentScreen = "signIn"}
                        )
                    }
                }
            }
        }
        "detailScreen" -> {
            selectedArticle?.let { article ->
                ArticleDetailScreen(
                    article = article,
                    onSaveFavourite = { currentScreen = "newsScreen" },
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
