package com.example.hallreservationsystem.ui.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hallreservationsystem.firebase.FirebaseHelper
import com.example.hallreservationsystem.model.Reservation
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationScreen(navController: NavController) {
    val context = LocalContext.current

    var hall by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("user@example.com") } // Replace with authenticated user email
    var course by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val firebaseHelper = FirebaseHelper()

    val datePickerState = remember { mutableStateOf(false) }
    val formattedDate = remember { mutableStateOf(date.format(DateTimeFormatter.ISO_DATE)) }

    val timeRegex = Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$") // Time validation format (HH:MM)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Reservation", fontWeight = FontWeight.Bold) },
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp)
        ) {
            // Hall Input
            OutlinedTextField(
                value = hall,
                onValueChange = { hall = it },
                label = { Text("Hall") },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp), // Darker text
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                singleLine = true,
            )

            // Date Picker
            OutlinedTextField(
                value = formattedDate.value,
                onValueChange = {},
                label = { Text("Date (YYYY-MM-DD)") },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                trailingIcon = {
                    TextButton(onClick = { datePickerState.value = true }) {
                        Text("Select Date")
                    }
                }
            )

            // Time Input Fields
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time (HH:MM)") },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = startTime.isNotEmpty() && !startTime.matches(timeRegex)
                )
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time (HH:MM)") },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = endTime.isNotEmpty() && !endTime.matches(timeRegex)
                )
            }

            // Course Input
            OutlinedTextField(
                value = course,
                onValueChange = { course = it },
                label = { Text("Course") },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                singleLine = true
            )

            // Submit Button
            Button(
                onClick = {
                    when {
                        hall.isEmpty() -> message = "Please enter a hall"
                        startTime.isEmpty() || endTime.isEmpty() -> message = "Please enter start and end times"
                        !startTime.matches(timeRegex) || !endTime.matches(timeRegex) -> message = "Invalid time format (use HH:MM)"
                        course.isEmpty() -> message = "Please enter a course"
                        else -> {
                            val reservation = Reservation(
                                id = "",
                                userId = userEmail,
                                hallId = hall,
                                date = date.toString(),
                                timeSlot = "$startTime - $endTime",
                                course = course
                            )

                            firebaseHelper.addReservation(reservation) { success ->
                                message = if (success) "Reservation added successfully!" else "Failed to add reservation"
                                if (success) {
                                    navController.popBackStack() // Navigate back after successful submission
                                }
                            }
                        }
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
                color = when {
                    message.contains("successfully") -> MaterialTheme.colorScheme.primary
                    message.isNotEmpty() -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onBackground
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    // Date Picker Dialog
    if (datePickerState.value) {
        DatePickerDialog(
            onDismissRequest = { datePickerState.value = false },
            initialDate = date,
            onDateSelected = {
                date = it
                formattedDate.value = date.format(DateTimeFormatter.ISO_DATE)
                datePickerState.value = false
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.time = Date.from(initialDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant())

    val datePicker = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    DisposableEffect(Unit) {
        datePicker.show()
        onDispose { datePicker.dismiss() }
    }

    datePicker.setOnDismissListener { onDismissRequest() }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ReservationScreenPreview() {
    ReservationScreen(navController = rememberNavController())
}
