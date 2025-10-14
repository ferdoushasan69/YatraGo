package com.dmmeta.choloja.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dmmeta.choloja.presentation.screen.map.SharedMapViewModel

expect class MapView {
    fun showCurrentLocation(latitude: Double, longitude: Double)
    fun addMapMarker(latitude: Double, longitude: Double, title: String?)
    fun requestLocationPermission(onPermissionGranted: () -> Unit)
    fun getCurrentLocation(onLocationReceived: (LatLng) -> Unit)
    fun getLocationName(latitude: Double, longitude: Double, onResult: (String?) -> Unit)
    fun setLocationByName(name: String, onResult: (String?) -> Unit)

}

data class LatLng(val latitude: Double, val longitude: Double)


@Composable
expect fun PlatformMapView(
    modifier: Modifier,
    viewModel: SharedMapViewModel,
    onMapReady: (MapView) -> Unit,
    onResult: (String?) -> Unit
)