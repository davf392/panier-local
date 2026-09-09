package com.davf392.panierlocal.core.utils

import kotlinx.datetime.LocalDateTime

actual fun formatDateTime(start: LocalDateTime, end: LocalDateTime): Pair<String, String> {
    return Pair("${start.dayOfMonth}/${start.monthNumber}/${start.year}", "${start.hour}h - ${end.hour}h")
}
