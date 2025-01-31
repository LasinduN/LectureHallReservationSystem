package com.example.hallreservationsystem.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Welcome Back!",
                    style = MaterialTheme.typography.h4,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false
                        errorMessage = null
                    },
                    label = { Text("Email") },
                    isError = emailError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (emailError) Color.Red else Color(0xFF0D47A1),
                        unfocusedBorderColor = if (emailError) Color.Red else Color.Gray
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = false
                        errorMessage = null
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (passwordError) Color.Red else Color(0xFF0D47A1),
                        unfocusedBorderColor = if (passwordError) Color.Red else Color.Gray
                    )
                )

                if (errorMessage != null) {
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text(text = errorMessage ?: "Unknown error", style = MaterialTheme.typography.body2)
                    }
                }

                Button(
                    onClick = {
                        if (email.isBlank()) {
                            emailError = true
                            errorMessage = "Email cannot be empty"
                            return@Button
                        }
                        if (password.isBlank()) {
                            passwordError = true
                            errorMessage = "Password cannot be empty"
                            return@Button
                        }

                        isLoading = true
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                isLoading = false
                                if (task.isSuccessful) {
                                    navController.navigate("dashboard")
                                } else {
                                    errorMessage = task.exception?.message ?: "Login failed"
                                }
                            }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D47A1)),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Sign In", color = Color.White)
                    }
                }

                TextButton(onClick = { navController.navigate("register") }) {
                    Text(
                        text = "Create an Account",
                        color = Color(0xFF0D47A1)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        LoginScreen(navController = navController)
    }
}
