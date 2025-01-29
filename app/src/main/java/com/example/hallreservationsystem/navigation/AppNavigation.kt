package com.example.hallreservationsystem.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hallreservationsystem.ui.screens.WelcomeScreen
import com.example.hallreservationsystem.ui.screens.LoginScreen
import com.example.hallreservationsystem.ui.screens.DashboardScreen
import com.example.hallreservationsystem.ui.screens.RegisterScreen
import com.example.hallreservationsystem.ui.screens.ReservationScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("reservation") {
            ReservationScreen(navController = navController)
        }

    }
}