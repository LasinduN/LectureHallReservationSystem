package com.example.hallreservationsystem.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hallreservationsystem.firebase.FirebaseHelper
import com.example.hallreservationsystem.model.Reservation

@Composable
fun ReservationScreen(navController: NavController) {
    var hall by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("user@example.com") } // You can get this from user authentication
    var message by remember { mutableStateOf("") }
    val firebaseHelper = FirebaseHelper()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add Reservation", style = MaterialTheme.typography.headlineMedium)

        // Text Fields for hall, date, and time
        OutlinedTextField(
            value = hall,
            onValueChange = { hall = it },
            label = { Text("Hall") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Time (HH:MM)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Submit Button
        Button(
            onClick = {
                if (hall.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                    // Create Reservation object
                    val reservation = Reservation(
                        id = "", // Firebase auto-generates ID
                        userId = userEmail, // or use a real user ID if available
                        hallId = hall,
                        date = date,
                        timeSlot = time,
                        status = "Pending" // Default status
                    )

                    // Call firebaseHelper to save reservation
                    firebaseHelper.addReservation(reservation) { success ->
                        message = if (success) "Reservation added!" else "Failed to add reservation"
                    }
                } else {
                    message = "Please fill all fields"
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Message Display
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (message.contains("added")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationScreenPreview() {
    ReservationScreen(navController = rememberNavController())
}
