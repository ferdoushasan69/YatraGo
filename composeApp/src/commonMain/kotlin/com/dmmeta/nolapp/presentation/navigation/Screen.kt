package com.dmmeta.nolapp.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Screen {

    @Serializable
    object Home : Screen()

    @Serializable
    data class Category(val categoryName: String) : Screen()

    @Serializable
    object ViewAllBanner : Screen()

    @Serializable
    object Search : Screen()

    @Serializable
    object Map : Screen()

    @Serializable
    object RoundMap : Screen()

    @Serializable
    object Favorite : Screen()

    @Serializable
    object Profile : Screen()
}