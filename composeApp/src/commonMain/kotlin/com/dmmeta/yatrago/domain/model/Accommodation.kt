// shared/domain/model/Accommodation.kt
package com.dmmeta.yatrago.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Accommodation(
    val id: Long? = null,
    val name: String,
    val stars: Int,
    val address: String,
    val cityLine: String,
    val photoUrl: String,
    val description: String,
    val facilities: List<String>,
    val mainPolicy: String,
    val importantInfo: String,
    val sellerName: String,
    val priceKrw: String,
    val discount: String
)