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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            painter = painterResource(id = R.drawable.wave_top),
            contentDescription = "Top Wave",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth // Stretch to fill the width
        )


        // Welcome message at the top-left
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.h4,
            color = Color(0xFF0D47A1), // Dark blue color
            modifier = Modifier
                .padding(top = 60.dp, start = 24.dp)
        )

        // Title and buttons centered on the screen
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // App logo or icon
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(300.dp)
                    .offset(y = (-30).dp)
            )

            // Login Button (Primary Action)
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth(0.7f) // Slightly wider
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(12.dp), // Softer rounded corners
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0D47A1), // Deep Blue
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(8.dp) // Adds depth with shadow
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(20.dp)) // More space between buttons

            // Register Button (Secondary Action)
            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF42A5F5), // Light Blue
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(8.dp)
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
