package com.davf392.panierlocal.ui.composition

import androidx.compose.runtime.compositionLocalOf
import com.davf392.panierlocal.data.DistributionLocation
import kotlinx.datetime.LocalDateTime

data class DistributionContext(
    val currentLocation: DistributionLocation,
    val locations: List<DistributionLocation>,
    val onLocationSelected: (DistributionLocation) -> Unit,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)

val LocalDistributionContext = compositionLocalOf<DistributionContext?> { null }
