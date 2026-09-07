package com.davf392.panierlocal.data.staff_dashboard

data class PermanenceSlot(
    val id: String,
    val name: String,
    val role: String, // e.g., "Salarié", "Producteur, "Bénévole"
    val isPresent: Boolean
)
