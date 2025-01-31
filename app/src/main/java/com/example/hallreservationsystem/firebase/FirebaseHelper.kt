package com.example.hallreservationsystem.firebase

import com.example.hallreservationsystem.model.Reservation
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()

    // Fetch all reservations
    fun getReservations(onComplete: (List<Reservation>) -> Unit) {
        db.collection("reservations")
            .get()
            .addOnSuccessListener { result ->
                val reservations = result.map { document ->
                    //Convert Firestore document into a Reservation object
                    Reservation(
                        id = document.id,  // Firebase auto-generated ID
                        userId = document.getString("userId") ?: "",
                        hallId = document.getString("hallId") ?: "",
                        date = document.getString("date") ?: "",
                        timeSlot = document.getString("timeSlot") ?: "",
                        course = document.getString("course") ?: ""
                    )
                }
                onComplete(reservations)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())  // Handle failure, maybe show a message
            }
    }

    // Add a new reservation
    fun addReservation(reservation: Reservation, onComplete: (Boolean) -> Unit) {
        db.collection("reservations")
            .add(
                hashMapOf(
                    "userId" to reservation.userId,
                    "hallId" to reservation.hallId,
                    "date" to reservation.date,
                    "timeSlot" to reservation.timeSlot,
                    "course" to reservation.course
                )
            )
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
}
