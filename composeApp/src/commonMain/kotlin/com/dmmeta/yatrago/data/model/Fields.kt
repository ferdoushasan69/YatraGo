package com.dmmeta.yatrago.data.model

import com.dmmeta.yatrago.database.AccommodationEntity
import com.dmmeta.yatrago.domain.model.Accommodation

data class Fields(
    val id: Long?,
    val name: String,
    val location: String,
    val discount_price: Double,
    val total_price: Double
)

fun AccommodationEntity.toDomain() = Accommodation(
    id = id,
    name = name,
    location = location,
    discountPrice = discount_price,
    totalPrice = total_price
)
