package com.davf392.panierlocal.data.member

data class Member(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val needsRenewal: Boolean, // Abonnement web à relancer
    val hasArrears: Boolean,    // Dette monétaire
    val notes: String? = null
)
