package com.dmmeta.nolapp.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Screen {

    @Serializable
    object Home : Screen()

    @Serializable
    object Category : Screen()

    @Serializable
    object RoundMap : Screen()

    @Serializable
    object Favorite : Screen()

    @Serializable
    object Profile : Screen()
}