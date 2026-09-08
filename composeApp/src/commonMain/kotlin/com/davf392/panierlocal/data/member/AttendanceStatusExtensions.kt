package com.davf392.panierlocal.data.member

fun AttendanceStatus.toFrench(): String {
    return when (this) {
        AttendanceStatus.EXPECTED -> "Attendu"
        AttendanceStatus.COLLECTED -> "Récupéré"
        AttendanceStatus.ABSENT -> "Absent"
        AttendanceStatus.REDIRECTED -> "Redirigé"
    }
}
