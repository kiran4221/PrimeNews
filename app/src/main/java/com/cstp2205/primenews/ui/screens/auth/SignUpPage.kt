package com.cstp2205.primenews.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cstp2205.primenews.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    // State to toggle password visibility
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "SIGN UP", fontSize = 40.sp)
        Spacer(modifier = Modifier.size(30.dp))

        TextField(
            value = authViewModel.userName,
            onValueChange = { authViewModel.userName = it },
            placeholder = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = authViewModel.userEmail,
            onValueChange = { authViewModel.userEmail = it },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))

        // Updated password field with toggle for password visibility.
        TextField(
            value = authViewModel.userPassword,
            onValueChange = { authViewModel.userPassword = it },
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.size(20.dp))

        authViewModel.errorMessage?.let { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.size(15.dp))
        }

        Button(onClick = {
            coroutineScope.launch {
                val success = authViewModel.signUp()
                if (success) {
                    onSignUpSuccess()
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Sign up")
        }

        Spacer(modifier = Modifier.size(15.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.size(15.dp))

        Text("Already have an account?")
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = { onNavigateToSignIn() }, modifier = Modifier.fillMaxWidth()) {
            Text("Log in")
        }
    }
}
