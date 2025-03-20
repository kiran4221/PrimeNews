package com.cstp2205.primenews.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "No name Provided"
    val userEmail = user?. email ?: "Email not provided"

    Scaffold (
        content = {innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "User Profile", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(18.dp))

                Text(text = "Name: $userName", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Email: $userEmail", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Logout", style= MaterialTheme.typography.bodySmall)
                }
            }

        }
    )
}