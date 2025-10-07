package com.dmmeta.yatrago.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Home : Screen()

    @Serializable
    data class Category(val categoryName: String) : Screen()

    @Serializable
    data object CategorySelection

    @Serializable
    object ViewAllBanner : Screen()

    @Serializable
    data class Search(val query: String = "") : Screen()

    @Serializable
    data object SearchFilter : Screen()

    @Serializable
    object Map : Screen()

    @Serializable
    data class AccommodationDetails(val json : String = "-1") : Screen()

    @Serializable
    object RoundMap : Screen()

    @Serializable
    object Favorite : Screen()

    @Serializable
    object Profile : Screen()
}