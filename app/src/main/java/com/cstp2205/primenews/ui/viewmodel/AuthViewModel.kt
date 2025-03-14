package com.cstp2205.primenews.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Data class to store additional user info in Firestore.
data class UserInfo(
    val firstName: String,
    val email: String
)

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var userName by mutableStateOf("")
    var userEmail by mutableStateOf("")
    var userPassword by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    var currentUser by mutableStateOf<FirebaseUser?>(auth.currentUser)

    suspend fun signUp(): Boolean {
        isLoading = true
        errorMessage = null

        try {
            val result = auth.createUserWithEmailAndPassword(userEmail, userPassword).await()
            currentUser = result.user

            currentUser?.let { user ->

                val userData = UserInfo(
                    firstName = userName,
                    email = userEmail
                )
                db.collection("users").document(user.uid).set(userData).await()
            }
            return true
        } catch (e: Exception) {
            errorMessage = e.localizedMessage
            return false
        } finally {
            isLoading = false
        }
    }

    suspend fun signIn(): Boolean {
        isLoading = true
        errorMessage = null

        try {
            val result = auth.signInWithEmailAndPassword(userEmail, userPassword).await()
            currentUser = result.user
            return true
        } catch (e: Exception) {
            errorMessage = e.localizedMessage
            return false
        } finally {
            isLoading = false
        }
    }

    fun signOut() {
        auth.signOut()
        currentUser = null
    }
}

