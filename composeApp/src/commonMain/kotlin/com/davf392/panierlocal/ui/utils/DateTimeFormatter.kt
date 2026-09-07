package com.davf392.panierlocal.ui.utils

import kotlinx.datetime.LocalDateTime

expect fun formatDateTime(start: LocalDateTime, end: LocalDateTime): Pair<String, String>
