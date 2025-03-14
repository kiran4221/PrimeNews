package com.cstp2205.primenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
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
import com.cstp2205.primenews.ui.screens.auth.SignInScreen
import com.cstp2205.primenews.ui.screens.auth.SignUpScreen
import com.cstp2205.primenews.ui.screens.news.NewsScreen
import com.cstp2205.primenews.ui.theme.PrimeNewsTheme
import com.google.firebase.auth.ActionCodeUrl

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

   var currentScreen by remember { mutableStateOf("signIn")}

    when (currentScreen) {
        "signIn" -> SignInScreen(
            onSignInSuccess = {currentScreen = "newsScreen"},
            onNavigateToSignUp = {currentScreen = "signUp"}
        )

        "signUp" -> SignUpScreen(
            onSignUpSuccess = {currentScreen = "newsScreen"},
            onNavigateToSignIn = {currentScreen = "signIn"}
        )

        /*"newsScreen" -> NewsScreen(
            onSignOut = { currentScreen = "signIn"}
        )*/
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrimeNewsTheme {
        PrimeNewsApp()
    }
}