// shared/domain/model/Accommodation.kt
package com.dmmeta.yatrago.domain.model

data class Accommodation(
    val id: Long? = null,
    val name: String,
    val location: String,
    val discountPrice: String,
    val totalPrice: String,
)
