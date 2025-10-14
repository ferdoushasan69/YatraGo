// data/model/Mappers.kt
package com.dmmeta.choloja.data.model

import com.dmmeta.choloja.domain.model.Accommodation
import com.dmmeta.yatrago.database.AccommodationEntity

private fun String.toDoubleOrZero(): Double =
    toDoubleOrNull() ?: 0.0


fun AccommodationEntity.toDomain(): Accommodation =
    Accommodation(
        id = id,
        name = name,
        stars = stars.toInt(),
        cityLine = cityLine,
        address = address,
        photoUrl = photoUrl,
        description = description,
        facilities = facilities.split(","),
        mainPolicy = mainPolicy,
        importantInfo = importantInfo,
        sellerName = sellerName,
        priceKrw = priceKrw,
        discount = discount
    )
