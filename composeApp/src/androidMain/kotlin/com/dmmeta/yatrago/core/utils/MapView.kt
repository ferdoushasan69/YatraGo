package com.dmmeta.yatrago.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dmmeta.yatrago.presentation.screen.map.SharedMapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale
import com.google.android.gms.maps.MapView as GmsMapView
import com.google.android.gms.maps.model.LatLng as AndroidLatLng

actual class MapView(
    private val context: Context,
    val googleMap: GoogleMap
) {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    actual fun showCurrentLocation(latitude: Double, longitude: Double) {
        val location = AndroidLatLng(latitude, longitude)

        googleMap.addMarker(
            MarkerOptions()
                .position(location)
                .title("Current Location")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    actual fun addMapMarker(latitude: Double, longitude: Double, title: String?) {
        val location = AndroidLatLng(latitude, longitude)
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(title ?: "Nearby Location")
        )
        marker?.showInfoWindow()
    }

    actual fun requestLocationPermission(onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGranted()
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    actual fun getCurrentLocation(onLocationReceived: (LatLng) -> Unit) {

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        onLocationReceived(LatLng(it.latitude, it.longitude))
                    }
                }
        }
    }

    actual fun setLocationByName(name: String, onResult: (String?) -> Unit) {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(name, 1)

        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val latLng = AndroidLatLng(address.latitude, address.longitude)

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(name)
            )
            onResult(address.getAddressLine(0) ?: name)
            Log.d("setLocationByName", "setLocationByName: $name")
        }
    }

    actual fun getLocationName(latitude: Double, longitude: Double, onResult: (String?) -> Unit) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses?.firstOrNull()?.getAddressLine(0)
            onResult(address)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(null)
        }
    }


}

@RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@Composable
actual fun PlatformMapView(
    modifier: Modifier,
    viewModel: SharedMapViewModel,
    onMapReady: (MapView) -> Unit,
    onResult: (String?) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var mapWrapper by remember { mutableStateOf<MapView?>(viewModel.cachedMapView) }
    var mapView by remember { mutableStateOf<GmsMapView?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mapWrapper?.getCurrentLocation { location ->
//                mapWrapper?.showCurrentLocation(location.latitude, location.longitude)
            }
        }
    }
    //Handle lifecycle
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            mapView?.let { view ->
                when (event) {
                    Lifecycle.Event.ON_START -> view.onStart()
                    Lifecycle.Event.ON_RESUME -> view.onResume()
                    Lifecycle.Event.ON_PAUSE -> view.onPause()
                    Lifecycle.Event.ON_STOP -> view.onStop()
                    Lifecycle.Event.ON_DESTROY -> view.onDestroy()
                    else -> {}
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView?.onDestroy()
        }
    }

    AndroidView(
        factory = { ctx ->
            val gmsMapView = GmsMapView(ctx)
            mapView = gmsMapView
            gmsMapView.onCreate(null)


            gmsMapView.getMapAsync { googleMap ->
                googleMap.apply {
                    uiSettings.isZoomControlsEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true

                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        isMyLocationEnabled = true
                    }
                }

                val wrapper =
                    mapWrapper ?: MapView(ctx, googleMap = googleMap).also { mapWrapper = it }
                mapWrapper = wrapper
                onMapReady(wrapper)

                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    wrapper.getCurrentLocation { location ->
//                            wrapper.showCurrentLocation(location.latitude, location.longitude)
                    }
                }
                googleMap.setOnMapClickListener { latLng ->
                    googleMap.clear()
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title("Selected Location")
                    )
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    )
                    wrapper.getLocationName(
                        latitude = latLng.latitude,
                        longitude = latLng.longitude,
                        onResult = { locationName ->
                            Log.d("LocationName", "PlatformMapView: $locationName")
                            onResult(locationName)
                        })
                }
            }
            gmsMapView
        },
        modifier = modifier,
        update = { view ->
            view
        }
    )
}