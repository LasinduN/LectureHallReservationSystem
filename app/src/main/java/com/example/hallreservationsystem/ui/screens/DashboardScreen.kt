package com.example.hallreservationsystem.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

    val context = LocalContext.current
    var backPressedTime by remember { mutableStateOf(0L) }

    // Handle double back press to exit
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            // Close the app
            android.os.Process.killProcess(android.os.Process.myPid())
        } else {
            backPressedTime = currentTime
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        firebaseHelper.getReservations { reservations ->
            reservationList = reservations
        }
    }

    // Get current date
    val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

    // Filter reservations to show only today's
    val todayReservations = reservationList.filter {
        it.date == currentDate
    }

    Scaffold(
        topBar = { DashboardTopBar(navController) },
        floatingActionButton = { AddReservationButton(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F4F8))
                .padding(padding)
        ) {
            WelcomeSection()
            TodayScheduleHeader()
            ReservationList(reservations = todayReservations)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(navController: NavController) {
    TopAppBar(
        title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF1565C0),
            titleContentColor = Color.White,
        )
    )
}

@Composable
fun AddReservationButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate("reservation") },
        containerColor = Color(0xFF1E88E5),
        modifier = Modifier.padding(16.dp)
    ) {
        Text("+", color = Color.White, style = MaterialTheme.typography.headlineMedium)
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
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
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
            color = Color(0xFF1E88E5),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = formattedDate,
            color = Color.Gray,
            style = MaterialTheme.typography.titleMedium
        )
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
                month = reservation.date.split("-")[1],
                day = reservation.date.split("-")[2],
                title = reservation.hallId,
                time = reservation.timeSlot,
                course = reservation.course
            )
        }
    }
}

@Composable
fun ScheduleItem(
    month: String,
    day: String,
    title: String,
    time: String,
    course: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(60.dp)
                    .background(Color(0xFF1E88E5), RoundedCornerShape(3.dp))
            )

            Column(
                modifier = Modifier.padding(horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {}

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E88E5)
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = course,
                    style = MaterialTheme.typography.titleSmall,
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
