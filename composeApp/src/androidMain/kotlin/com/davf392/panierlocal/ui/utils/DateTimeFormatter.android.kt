package com.davf392.panierlocal.ui.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.time.ZoneId

actual fun formatDateTime(start: LocalDateTime, end: LocalDateTime): Pair<String, String> {
    val zoneId = ZoneId.systemDefault()
    val startInstant = java.time.Instant.ofEpochMilli(start.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
    val endInstant = java.time.Instant.ofEpochMilli(end.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
    
    val javaStart = java.time.ZonedDateTime.ofInstant(startInstant, zoneId)
    val javaEnd = java.time.ZonedDateTime.ofInstant(endInstant, zoneId)
    
    val datePattern = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.getDefault())
    val timePattern = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
    
    return Pair(
        javaStart.format(datePattern).replaceFirstChar { it.uppercase() },
        "${javaStart.format(timePattern)} - ${javaEnd.format(timePattern)}"
    )
}
