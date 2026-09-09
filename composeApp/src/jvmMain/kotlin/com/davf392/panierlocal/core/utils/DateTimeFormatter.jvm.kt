package com.davf392.panierlocal.core.utils

import kotlinx.datetime.LocalDateTime
import java.time.format.DateTimeFormatter

actual fun formatDateTime(start: LocalDateTime, end: LocalDateTime): Pair<String, String> {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return Pair(
        java.time.LocalDateTime.of(start.year, start.monthNumber, start.dayOfMonth, start.hour, start.minute).format(formatter),
        java.time.LocalDateTime.of(end.year, end.monthNumber, end.dayOfMonth, end.hour, end.minute).format(formatter)
    )
}
