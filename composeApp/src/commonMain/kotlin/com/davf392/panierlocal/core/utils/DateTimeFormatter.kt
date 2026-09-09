package com.davf392.panierlocal.core.utils

import kotlinx.datetime.LocalDateTime

expect fun formatDateTime(start: LocalDateTime, end: LocalDateTime): Pair<String, String>
