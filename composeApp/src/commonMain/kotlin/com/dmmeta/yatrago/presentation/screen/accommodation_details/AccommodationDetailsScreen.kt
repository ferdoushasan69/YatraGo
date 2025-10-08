package com.dmmeta.yatrago.presentation.screen.accommodation_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dmmeta.yatrago.core.utils.MapView
import com.dmmeta.yatrago.core.utils.PlatformMapView
import com.dmmeta.yatrago.domain.model.Accommodation
import com.dmmeta.yatrago.presentation.screen.component.CustomLocationItem
import com.dmmeta.yatrago.presentation.screen.component.CustomTopAppBar
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import yatrago.composeapp.generated.resources.Res
import yatrago.composeapp.generated.resources.ic_back
import yatrago.composeapp.generated.resources.ic_bed
import yatrago.composeapp.generated.resources.ic_favorite
import yatrago.composeapp.generated.resources.ic_frame
import yatrago.composeapp.generated.resources.ic_home
import yatrago.composeapp.generated.resources.ic_location
import yatrago.composeapp.generated.resources.ic_share


@Composable
fun AccommodationDetailsScreen(
    json: String,
    navController: NavHostController,
    viewModel: AccommodationDetailsViewModel = koinViewModel()
) {

    val data = Json.decodeFromString<Accommodation>(json)
    AccommodationContent(
        onBack = {
            navController.navigateUp()
        },
        addToCart = {
            viewModel.insert(accommodation = it)
        },
        hotel = data
    )


}

@Composable
fun AccommodationContent(
    onBack: () -> Unit,
    addToCart: (Accommodation) -> Unit,
    hotel: Accommodation
) {
    var mapWeightTarget by remember { mutableStateOf(1f) }
    var locationName by remember { mutableStateOf("") }
    var setMapView: MapView? by remember { mutableStateOf(null) }
    var mapReady by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    val sample =
        Accommodation(
            name = "엑조티카 상탄 하우스 바치료",
            stars = 3,
            address = "Exotica sampan",
            cityLine = "Cox’s Bazar, Bangladesh",
            photoUrl = "https://images.unsplash.com/photo-1568495248636-6432b97bd949?q=80&w=1200",
            description = "해변과 가까운 합리적인 3성급 숙소입니다. 깔끔한 객실과 친절한 직원, 기본 편의시설을 제공합니다.",
            facilities = listOf("프런트 데스크", "룸서비스", "공항 픽업/샌딩", "와이파이", "주차"),
            mainPolicy = "체크인 12:00 이후, 체크아웃 11:00 이전. 신분증 필요. 흡연은 지정된 구역에서만 가능.",
            importantInfo = "현지 사정에 따라 일부 시설 이용이 제한될 수 있습니다. 추가 요금이 발생할 수 있습니다.",
            sellerName = "NOL",
            priceKrw = "94,844원~",
            discount = "34093",
            id = 3
        )





    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = hotel.photoUrl,
                contentDescription = null,
                Modifier.height(300.dp),
                contentScale = ContentScale.FillBounds
            )
            CustomTopAppBar(
                navIcon = {
                    IconFit(
                        onClick = onBack, imageVector = Icons.Default.ArrowBackIosNew,
                        modifier = Modifier.background(Color.White.copy(.7f), shape = CircleShape)
                    )
                },
                topBarAction = {
                    IconFit(
                        onClick = {},
                        imageVector = Icons.Default.Home,
                        modifier = Modifier.background(Color.White.copy(.7f), shape = CircleShape)
                    )
                    IconFit(
                        onClick = {},
                        imageVector = Icons.Default.Share,
                        modifier = Modifier.background(Color.White.copy(.7f), shape = CircleShape)
                            .clip(CircleShape)
                    )


                }
            )


        }


        TitleBlock(
            stars = hotel.stars,
            name = hotel.name,
            address = hotel.address,
            cityLine = hotel.cityLine,
            addToCart = {
                val accommodation = Accommodation(
                    name = hotel.name,
                    stars = hotel.stars,
                    address = hotel.address,
                    cityLine = hotel.cityLine,
                    photoUrl = hotel.photoUrl,
                    description = hotel.description,
                    facilities = hotel.facilities,
                    mainPolicy = hotel.mainPolicy,
                    importantInfo = hotel.importantInfo,
                    sellerName = hotel.sellerName,
                    priceKrw = hotel.priceKrw,
                    discount = hotel.discount
                )
                addToCart(accommodation)
                isFavorite = true
            },
            isFavorite = isFavorite
        )

        LocationNameBlock(locationName = "${sample.address},${sample.cityLine}")
        if (!mapReady) {
            Box(
                Modifier.fillMaxWidth().height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        SectionCard(
            "위치 정보",
            content = {
                PlatformMapView(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                        .height(300.dp),
                    onMapReady = { mapView ->
                        setMapView = mapView
                        mapReady = true
                        mapView.setLocationByName(sample.cityLine) { result ->
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


            },
            onClick = {},
            locationName = locationName,
        )
    }
}


// ---------- Pieces ----------
@Composable
private fun PhotoHeader(url: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Image(
            painterResource(Res.drawable.ic_bed),
            contentDescription = "호텔 사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        // rounded overlay (optional)
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text("사진", fontSize = 12.sp)
        }
    }
}

@Composable
private fun TitleBlock(
    stars: Int,
    name: String,
    address: String,
    cityLine: String,
    addToCart: () -> Unit,
    isFavorite: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                buildAnnotatedString {
                    append("호텔 ")
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append("${stars}성급") }
                },
                fontSize = 13.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))

            Spacer(Modifier.width(4.dp))
            Text(
                "$address • $cityLine",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconFit(
            onClick = addToCart,
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.Favorite,
            isFavorite = isFavorite
        )
    }
}

@Composable
private fun LocationNameBlock(locationName: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(.4f),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                Icon(
                    painterResource(Res.drawable.ic_location),
                    modifier = Modifier.size(16.dp),
                    contentDescription = null
                )
            }
            Text(
                locationName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            TextButton(onClick = {}) {
                Text(
                    "지도보기",
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(.4f),
        )

        Image(
            painterResource(Res.drawable.ic_frame),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(80.dp),
            contentScale = ContentScale.Fit
        )
    }

}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
    onClick: () -> Unit,
    locationName: String
) {

    Column(Modifier.padding(16.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(12.dp))
        content()
        CustomLocationItem(
            locationName = locationName,
            isDetails = true,
            buttonText = "주소 복사",
            onClick = onClick
        )
    }
}


// ---------- Preview ----------
@Preview
@Composable
private fun AccommodationDetailPreview() {
    val sample = Accommodation(
        name = "시사이드 리조트 앤 스파",
        stars = 4,
        address = "Marine Drive Road",
        cityLine = "Cox's Bazar, Bangladesh",
        photoUrl = "https://unsplash.com/photos/hotel-room-photo-KPDbRyFOTnE",
        description = "바다 전망이 아름다운 4성급 리조트입니다. 스파, 수영장, 레스토랑을 갖추고 있습니다.",
        facilities = listOf("스파", "수영장", "레스토랑", "바", "피트니스센터", "와이파이"),
        mainPolicy = "체크인 14:00 이후, 체크아웃 12:00 이전. 여권 필요. 반려동물 동반 불가.",
        importantInfo = "성수기에는 최소 숙박 일수 제한이 있을 수 있습니다. 조식 별도.",
        sellerName = "Booking.com",
        priceKrw = "145,000원~",
        discount = "25000",
        id = 1,
    )
}


fun accommodationList(): List<Accommodation> {

    // Neighborhoods inside Cox’s Bazar (varied)
    val coxAreas = listOf(
        "Kolatoli Beach", "Sugandha Point", "Laboni Point", "Marine Drive",
        "Jhinuk Market", "Himchari", "Inani Beach", "Teknaf",
        "Jhilongja", "Bakkhali River", "Bus Terminal Area", "Old Town"
    )

    // Other Bangladeshi spots to avoid same cityLine everywhere
    val otherCities = listOf(
        "Saint Martin’s Island" to "Saint Martin’s, Bangladesh",
        "Kuakata Sea Beach" to "Kuakata, Bangladesh",
        "Patenga Beach" to "Chattogram, Bangladesh",
        "Rangamati Lake" to "Rangamati, Bangladesh",
        "Bandarban Hills" to "Bandarban, Bangladesh",
        "Sreemangal Tea Garden" to "Sylhet, Bangladesh",
        "Gulshan-2" to "Dhaka, Bangladesh",
        "Rajshahi City Center" to "Rajshahi, Bangladesh",
        "Khulna Riverside" to "Khulna, Bangladesh",
        "Barishal Launch Ghat" to "Barishal, Bangladesh",
        "CDA Avenue" to "Chattogram, Bangladesh",
        "Zinda Park" to "Gazipur, Bangladesh",
        "New Market" to "Dhaka, Bangladesh",
        "Airport Road" to "Sylhet, Bangladesh"
    )

    val unsplash = listOf(
        "https://images.unsplash.com/photo-1618773928121-c32242e63f39?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1507679799987-c73779587ccf?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1505691723518-36a5ac3b2f56?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1501117716987-c8e002e1e1e5?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1520256862855-398228c41684?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1560066984-138dadb4c035?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1540518614846-7eded433c457?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1551776235-dde6d4829808?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1496412705862-e0088f16f791?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1519710164239-da123dc03ef4?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1535827841776-24afc1e255ac?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1498661367879-6be9eb3c261a?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1552566626-52a9b56b58a5?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1552908352-9e64c6f58a9a?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1529070538774-1843cb3265df?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1600585154340-1e04d00c11c7?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0",
        "https://images.unsplash.com/photo-1554995207-c18c203602cb?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0"
    )

    val names1 = listOf(
        "오션뷰", "선셋", "선라이즈", "블루", "그랜드", "코지", "에코", "로얄", "트로피컬", "하버",
        "리버뷰", "시티 센터", "스카이라인", "패밀리", "비즈니스", "아트", "마운틴뷰", "스파", "포레스트", "팜"
    )
    val names2 = listOf("호텔", "리조트", "인", "스테이", "게스트하우스", "로지", "스위트", "레지던스", "하우스", "빌라")
    val sellers = listOf(
        "NOL",
        "Booking.com",
        "Agoda",
        "Expedia",
        "_root_ide_package_.com.dmmeta.yatrago.domain.model.Accommodations.com",
        "Trip.com",
        "Airbnb",
        "Luxury Escapes"
    )

    val facilitySets = listOf(
        listOf("프런트 데스크", "룸서비스", "와이파이", "주차"),
        listOf("레스토랑", "바", "수영장", "와이파이"),
        listOf("스파", "피트니스센터", "레스토랑", "와이파이"),
        listOf("프라이빗 비치", "레스토랑", "수영장", "와이파이"),
        listOf("가족룸", "키즈 클럽", "레스토랑", "와이파이")
    )

    return List(40) { i ->
        val inCox = i < 24
        val (address, city) = if (inCox) {
            coxAreas[i % coxAreas.size] to "Cox's Bazar, Bangladesh"
        } else {
            val p = otherCities[(i - 24) % otherCities.size]
            p.first to p.second
        }

        val name = "${names1[i % names1.size]} ${names2[(i / 2) % names2.size]}"
        val stars = 2 + (i % 4) // 2..5
        val basePrice = 70000 + ((i * 4200) % 280000)
        val discountPct = 5 + (i % 7) * 3
        val discountAmt = (basePrice * discountPct / 100.0).toInt()

        val desc = when {
            stars >= 5 -> "초호화 ${stars}성급 숙소입니다. 프리미엄 서비스와 최고급 편의시설을 제공합니다."
            stars == 4 -> "편안한 ${stars}성급 숙소입니다. 스파와 수영장, 다양한 식음 시설을 갖추고 있습니다."
            else -> "합리적인 ${stars}성급 숙소입니다. 깔끔한 객실과 기본 편의시설을 제공합니다."
        }

        val img =
            if (i < unsplash.size) unsplash[i] else "https://picsum.photos/seed/hotel$i/1200/800"

        Accommodation(
            id = (i + 1).toLong(),
            name = name,
            stars = stars,
            address = address,
            cityLine = city,
            photoUrl = img,
            description = desc,
            facilities = facilitySets[i % facilitySets.size],
            mainPolicy = "체크인 14:00 이후, 체크아웃 11:00 이전. 신분증 필요. 흡연은 지정된 구역에서만 가능.",
            importantInfo = "현지 사정에 따라 일부 시설 이용 제한 및 추가 요금이 발생할 수 있습니다.",
            sellerName = sellers[i % sellers.size],
            priceKrw = basePrice.toString(),
            discount = discountAmt.toString()
        )
    }
}


@Composable
fun AccommodationDetails() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {

        }
    }
}

@Composable
fun IconFit(
    onClick: () -> Unit,
    imageVector: ImageVector,
    isFavorite: Boolean = false,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = imageVector,
            tint = if (isFavorite) Color.Red else Color.Unspecified,
            contentDescription = null,
        )
    }
}