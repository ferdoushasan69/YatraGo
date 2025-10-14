package com.dmmeta.choloja

import androidx.compose.ui.window.ComposeUIViewController
import com.dmmeta.choloja.core.di.initKoin
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val isDarkMode =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(isDarkMode = isDarkMode)
}