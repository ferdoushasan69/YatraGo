package com.dmmeta.nolapp.presentation.screen.map

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dmmeta.nolapp.core.utils.MapView
import com.dmmeta.nolapp.core.utils.PlatformMapView
import com.dmmeta.nolapp.presentation.screen.component.CustomLocationItem
import com.dmmeta.nolapp.presentation.screen.component.CustomOutlineButton
import com.dmmeta.nolapp.presentation.screen.component.CustomTopAppBar
import com.dmmeta.nolapp.presentation.theme.PrimaryColor
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.ic_back
import nolapp.composeapp.generated.resources.ic_location
import nolapp.composeapp.generated.resources.ic_location_info
import org.jetbrains.compose.resources.painterResource

@Composable
fun MapScreen(navHostController: NavHostController) {

    MapContent(
        onBack = {
            navHostController.navigateUp()
        },
    )


}

@Composable
fun MapContent(onBack: () -> Unit) {
    var mapWeightTarget by remember { mutableStateOf(1f) }
    var locationName by remember { mutableStateOf("") }
    var setMapView: MapView? by remember { mutableStateOf(null) }
    val animatedMapWeight by animateFloatAsState(
        targetValue = mapWeightTarget,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "Animation Map Weight",
    )

    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomTopAppBar(
                title = "지도",
                modifier = Modifier,
                navIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(painterResource(Res.drawable.ic_back), contentDescription = null)
                    }
                },
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )
            PlatformMapView(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .weight(.7f),
                onMapReady = { mapView ->
                    setMapView = mapView
                    mapView.setLocationByName("Dhaka") { result ->
                        locationName = result ?: ""
                    }

                    mapView.requestLocationPermission {
                        mapView.getCurrentLocation { location ->
                            mapView.showCurrentLocation(
                                longitude = location.longitude,
                                latitude = location.latitude
                            )

                            mapView.getLocationName(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                onResult = {
                                    locationName = it ?: ""
                                }
                            )
                        }
                    }
                },
                onResult = {
                    locationName = it ?: ""
                },
            )
            CustomLocationItem(
                locationName = locationName,
                buttonText = "주소 복사",
                onClick = {

                }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CustomOutlineButton(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(Res.drawable.ic_location),
                    buttonText = "업체위치",
                    textColor = PrimaryColor,
                    iconTint = PrimaryColor,
                    onClick = {
                        setMapView?.setLocationByName("Dhaka") { result ->
                            locationName = result ?: ""
                        }
                    }
                )
                Spacer(Modifier.width(8.dp))
                CustomOutlineButton(
                    iconTint = PrimaryColor,
                    modifier = Modifier.weight(1f),
                    painter = painterResource(Res.drawable.ic_location_info),
                    buttonText = "길안내",
                    textColor = PrimaryColor,
                    onClick = {
                        setMapView?.setLocationByName("seoul") { result ->
                            locationName = result ?: ""
                        }
                    }
                )
            }
        }
    }
}

