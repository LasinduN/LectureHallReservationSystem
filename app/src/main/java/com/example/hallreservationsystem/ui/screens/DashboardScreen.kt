package com.example.hallreservationsystem.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hallreservationsystem.firebase.FirebaseHelper
import com.example.hallreservationsystem.model.Reservation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(navController: NavController) {
    val firebaseHelper = FirebaseHelper()
    var reservationList by remember { mutableStateOf<List<Reservation>>(emptyList()) }

    // Fetch data from Firebase on composition
    LaunchedEffect(Unit) {
        firebaseHelper.getReservations { reservations ->
            reservationList = reservations
        }
    }

    Scaffold(
        topBar = { DashboardTopBar() },
        floatingActionButton = { AddReservationButton(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            WelcomeSection()
            TodayScheduleHeader()
            ReservationList(reservationList)
        }
    }
}

@Composable
fun ReservationList(reservations: List<Reservation>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(reservations) { reservation ->
            ScheduleItem(
                month = reservation.date.split("-")[1],  // Assuming date is in "YYYY-MM-DD" format
                day = reservation.date.split("-")[2],
                title = reservation.hallId,
                time = reservation.timeSlot,
                label = reservation.status // Displaying status as label
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar() {
    TopAppBar(
        title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF0D47A1),
            titleContentColor = Color.White
        )
    )
}

@Composable
fun AddReservationButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate("reservation")
        },
        containerColor = Color(0xFF1E88E5),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "+",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun WelcomeSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome to the Hall Reservation System!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayScheduleHeader() {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    val formattedDate = currentDate.format(formatter)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TODAY'S SCHEDULE",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = formattedDate,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ScheduleItem(
    month: String,
    day: String,
    title: String,
    time: String,
    label: String
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Vertical accent line
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .background(Color(0xFF2196F3), RoundedCornerShape(2.dp))
            )

            // Date section
            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = month,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3)
                )
                Text(
                    text = day,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Main content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "@$time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Right side info
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen(navController = rememberNavController())
}
