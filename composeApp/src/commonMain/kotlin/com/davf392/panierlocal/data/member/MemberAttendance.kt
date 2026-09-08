package com.davf392.panierlocal.data.member

data class MemberAttendance(
    val distributionId: String,
    val member: Member,
    val basketFormulaIds: List<String>, // Liste des formules attendues
    val status: AttendanceStatus,
    val updatedAt: Long? = null // Timestamp de la dernière action
)
