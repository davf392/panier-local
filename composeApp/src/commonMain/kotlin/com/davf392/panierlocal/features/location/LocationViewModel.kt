package com.davf392.panierlocal.features.location

import androidx.lifecycle.ViewModel
import com.davf392.panierlocal.data.DistributionLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationViewModel : ViewModel() {
    val locations = listOf(
        DistributionLocation("dist-001", "Le Croiseur (Lyon 7)"),
        DistributionLocation("dist-002", "Cabanes (Lyon 8)")
    )

    private val _currentLocation = MutableStateFlow(locations.first())
    val currentLocation: StateFlow<DistributionLocation> = _currentLocation.asStateFlow()

    fun setLocation(location: DistributionLocation) {
        _currentLocation.value = location
    }
}