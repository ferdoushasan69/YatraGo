// data/model/Mappers.kt
package com.dmmeta.yatrago.data.model

import com.dmmeta.yatrago.database.AccommodationEntity
import com.dmmeta.yatrago.domain.model.Accommodation

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
