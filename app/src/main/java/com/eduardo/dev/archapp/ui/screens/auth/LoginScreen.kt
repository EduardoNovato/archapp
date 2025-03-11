package com.eduardo.dev.archapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduardo.dev.archapp.navegation.Screen
import com.eduardo.dev.archapp.ui.components.AppButton
import com.eduardo.dev.archapp.ui.components.AppTextField
import com.eduardo.dev.archapp.ui.components.SectionDivider

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo/Title
            Text(
                text = "ARCHITECT",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Project Management for Architects",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isLoginMode) "Sign In" else "Create Account",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AppTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        isPassword = true
                    )

                    if (isLoginMode) {
                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = { /* Handle forgot password */ },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Forgot Password?")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    AppButton(
                        text = if (isLoginMode) "Sign In" else "Create Account",
                        onClick = {
                            // In a real app, handle authentication here
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SectionDivider(title = "OR")

                    Spacer(modifier = Modifier.height(16.dp))

                    // Google Sign In Button
                    OutlinedButton(
                        onClick = { /* Handle Google sign in */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Google",
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Continue with Google",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Toggle between login and register
            TextButton(
                onClick = { isLoginMode = !isLoginMode }
            ) {
                Text(
                    text = if (isLoginMode)
                        "Don't have an account? Sign Up"
                    else
                        "Already have an account? Sign In",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
