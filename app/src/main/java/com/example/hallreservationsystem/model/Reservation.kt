package com.example.hallreservationsystem.model

data class Reservation(
    val id: String = "",
    val userId: String = "",
    val hallId: String = "",
    val date: String = "",
    val timeSlot: String = "",
    val course: String = "", // Default status
)
