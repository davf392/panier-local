package com.davf392.panierlocal.ui.composition

import androidx.compose.runtime.compositionLocalOf
import com.davf392.panierlocal.viewmodel.location.Location

data class DistributionContext(
    val currentLocation: Location,
    val locations: List<Location>,
    val onLocationSelected: (Location) -> Unit
)

val LocalDistributionContext = compositionLocalOf<DistributionContext?> { null }
