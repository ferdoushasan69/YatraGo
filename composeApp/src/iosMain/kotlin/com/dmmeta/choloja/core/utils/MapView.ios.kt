package com.dmmeta.choloja.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import com.dmmeta.choloja.presentation.screen.map.SharedMapViewModel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.CLPlacemark
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.MapKit.MKCoordinateRegionMake
import platform.MapKit.MKCoordinateSpanMake
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation
import platform.MapKit.MKUserTrackingModeFollow
import platform.UIKit.UIGestureRecognizer
import platform.darwin.NSObject

actual class MapView(
    private val mapView: MKMapView
) : NSObject(), CLLocationManagerDelegateProtocol {

    private val locationManager = CLLocationManager().apply {
        desiredAccuracy = kCLLocationAccuracyBest
        delegate = this@MapView
    }

    private var onPermissionGranted: (() -> Unit)? = null

    private var onLocationReceived: ((LatLng) -> Unit)? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun showCurrentLocation(latitude: Double, longitude: Double) {
        val coordinate = CLLocationCoordinate2DMake(latitude = latitude, longitude = longitude)

        val span = MKCoordinateSpanMake(0.5, 0.5)
        val region = MKCoordinateRegionMake(coordinate, span)
        mapView.setRegion(region = region, animated = true)

        mapView.removeAnnotations(mapView.annotations)

        val annotation = MKPointAnnotation().apply {
            setCoordinate(coordinate)
            setTitle("Current Location")
        }

        mapView.addAnnotation(annotation)
        mapView.setShowsUserLocation(true)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun addMapMarker(
        latitude: Double,
        longitude: Double,
        title: String?
    ) {
        val coordinate = CLLocationCoordinate2DMake(latitude, longitude)
        val annotation = MKPointAnnotation().apply {
            setCoordinate(coordinate)
            setTitle(title)
        }
        mapView.addAnnotation(annotation)
    }

    actual fun requestLocationPermission(onPermissionGranted: () -> Unit) {
        this.onPermissionGranted = onPermissionGranted

        when (locationManager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedWhenInUse,
            kCLAuthorizationStatusAuthorizedAlways -> {
                onPermissionGranted()
                locationManager.requestLocation()
            }

            kCLAuthorizationStatusNotDetermined -> {
                locationManager.requestWhenInUseAuthorization() // This triggers the popup
            }

            else -> {
                println("Location permission denied or restricted")
            }
        }
    }

    actual fun getCurrentLocation(onLocationReceived: (LatLng) -> Unit) {
        this.onLocationReceived = onLocationReceived

        if (locationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedWhenInUse ||
            locationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedAlways
        ) {
            locationManager.requestLocation()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {

        val location = didUpdateLocations.firstOrNull() as CLLocation
        location?.let {
            val coordinate = it.coordinate
            val latitude = coordinate.useContents { latitude }
            val longitude = coordinate.useContents { longitude }
            onLocationReceived?.invoke(LatLng(latitude = latitude, longitude = longitude))
        }
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        println("Location error: ${didFailWithError.localizedDescription}")
    }

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        when (manager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedWhenInUse,
            kCLAuthorizationStatusAuthorizedAlways -> {
                onPermissionGranted?.invoke()
                manager.requestLocation()
            }

            else -> {}
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun setLocationByName(name: String, onResult: (String?) -> Unit) {
        val geocoder = CLGeocoder()
        geocoder.geocodeAddressString(name) { placemarks: List<*>?, error: NSError? ->
            if (error != null) {
                println(" Geocoding error: ${error.localizedDescription}")
                return@geocodeAddressString
            }
            val placemark = placemarks?.firstOrNull() as CLPlacemark
            val coordinate = placemark.location?.coordinate

            if (coordinate != null) {
                val latitude = coordinate.useContents { latitude }
                val longitude = coordinate.useContents { longitude }

                val annotation = MKPointAnnotation().apply {
                    setCoordinate(coordinate)
                    setTitle(name)
                }
                mapView.removeAnnotations(mapView.annotations)
                mapView.addAnnotation(annotation)

                val region = MKCoordinateRegionMake(
                    coordinate,
                    MKCoordinateSpanMake(latitudeDelta = 0.5, longitudeDelta = 0.5)
                )
                mapView.setRegion(region, true)
                println("Location set : $name latitude - $latitude longitude - $longitude")
            }

        }
    }

    actual fun getLocationName(
        latitude: Double,
        longitude: Double,
        onResult: (String?) -> Unit
    ) {
        val geoCoder = CLGeocoder()
        val location = CLLocation(latitude = latitude, longitude = longitude)
        geoCoder.reverseGeocodeLocation(location) { placeMarks: List<*>?, error: NSError? ->
            if (error != null) {
                println("Geocoding error: ${error.localizedDescription}")
                onResult(null)
            } else {

                val placeMark = placeMarks?.firstOrNull() as CLPlacemark

                val name = listOfNotNull(
                    placeMark.name,
                    placeMark.locality,
                    placeMark.country
                ).joinToString(", ")
                onResult(name.ifEmpty { null })
            }

        }
    }

}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformMapView(
    modifier: Modifier,
    viewModel: SharedMapViewModel,
    onMapReady: (MapView) -> Unit,
    onResult: (String?) -> Unit
) {
    var lat by  remember { mutableStateOf(0.0) }
    var long by  remember { mutableStateOf(0.0) }
    val mapView = remember {
        MKMapView().apply {
            setShowsUserLocation(true)
            setUserTrackingMode(MKUserTrackingModeFollow, animated = true)
        }
    }
    val mapWrapper = remember { MapView(mapView) }
    LaunchedEffect(Unit) {
        mapWrapper.requestLocationPermission {
            mapWrapper.getCurrentLocation { location ->
//                mapWrapper.showCurrentLocation(location.latitude, location.longitude)
            }

        }
    }

    UIKitView(
        factory = { mapView },
        modifier = modifier,
        update = { view -> view },
    )

    LaunchedEffect(Unit) {
        onMapReady(mapWrapper)
        mapWrapper.getLocationName(latitude = lat, longitude = long) { location ->
//            mapWrapper.showCurrentLocation(location.latitude, location.longitude)
            onResult(location)
            println("IOS get location : $location")
            println("IOS get location : $lat $long")
        }
    }


    val handle = remember(mapView) {
        TapHandler(mapView) { coordinate ->
            val latitude = coordinate.useContents { latitude }
            val longitude = coordinate.useContents { longitude }
            mapWrapper.addMapMarker(
                latitude = latitude,
                longitude = longitude,
                title = null
            )
            mapWrapper.getLocationName(latitude = latitude, longitude = longitude) {
                onResult(it)
                lat = latitude
                long = longitude
                println("IOS get location : $it")
            }

            mapView.removeAnnotations(mapView.annotations)

            val annotation = MKPointAnnotation().apply {
                setCoordinate(coordinate)
                setTitle("Selected Location")
            }
            mapView.addAnnotation(annotation)

            mapWrapper
        }
    }

    val recognizer = platform.UIKit.UITapGestureRecognizer(
        target = handle,
        action = platform.Foundation.NSSelectorFromString("handleTap:")
    )
    mapView.addGestureRecognizer(recognizer)
}

class TapHandler @OptIn(ExperimentalForeignApi::class) constructor(
    private val mapView: MKMapView,
    private val onTapCoord: (CValue<CLLocationCoordinate2D>) -> Unit
) : NSObject() {

    @OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
    @ObjCAction
    fun handleTap(gesture: UIGestureRecognizer) {
        val point = gesture.locationInView(mapView)
        val coord: CValue<CLLocationCoordinate2D> =
            mapView.convertPoint(point, toCoordinateFromView = mapView)
        onTapCoord(coord)
    }
}