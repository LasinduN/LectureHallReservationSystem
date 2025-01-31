package com.example.hallreservationsystem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.hallreservationsystem.navigation.AppNavigation
import com.example.hallreservationsystem.ui.theme.HallReservationSystemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        // Log Firebase initialization status
        Log.d("Firebase", "Initialized: ${FirebaseApp.getApps(this).size > 0}")

        setContent {
            HallReservationSystemTheme {

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController() // Declare the navController here
                    AppNavigation(navController = navController) // Pass navController to AppNavigation
                }
            }
        }
    }
}
