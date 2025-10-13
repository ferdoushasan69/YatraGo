package com.dmmeta.yatrago.presentation.screen.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dmmeta.yatrago.core.utils.LatLng
import com.dmmeta.yatrago.core.utils.MapView

class SharedMapViewModel : ViewModel() {

    val cachedMapView: MapView? = null
    val permissionGranted = mutableStateOf(false)
    val lastLocationName = mutableStateOf("")

    val lastLocation: LatLng? = null
}