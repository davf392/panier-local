package com.davf392.panierlocal.viewmodel.location

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Location(val id: String, val name: String)

class LocationViewModel : ViewModel() {
    val locations = listOf(
        Location("dist-001", "Le Croiseur (Lyon 7)"),
        Location("dist-002", "Cabanes (Lyon 8)")
    )
    
    private val _currentLocation = MutableStateFlow(locations.first())
    val currentLocation: StateFlow<Location> = _currentLocation.asStateFlow()
    
    fun setLocation(location: Location) {
        _currentLocation.value = location
    }
}
