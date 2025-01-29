package com.example.hallreservationsystem.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hallreservationsystem.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // Light blue background
    ) {
        // Decorative top wave
        Image(
            painter = painterResource(id = R.drawable.wave_top), // Add a wave vector asset
            contentDescription = "Top Wave",
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        // Welcome message at the top-left
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.h4,
            color = Color(0xFF0D47A1), // Dark blue color
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.TopStart)
        )

        // Title and buttons centered on the screen
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // App logo or icon
            Icon(
                painter = painterResource(id = R.drawable.ic_app_logo), // Add your app logo
                contentDescription = "App Logo",
                tint = Color(0xFF0D47A1), // Dark blue color
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Lab and Lecture Hall Reservation System",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = Color(0xFF0D47A1), // Dark blue color
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            // Login button
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D47A1)) // Dark blue color
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Register button
            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF42A5F5)) // Light blue color
            ) {
                Text(
                    text = "Register",
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }
        }

        // Decorative bottom wave
        Image(
            painter = painterResource(id = R.drawable.wave_bottom), // Add a wave vector asset
            contentDescription = "Bottom Wave",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController = navController)
}
