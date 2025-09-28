package com.dmmeta.yatrago

import androidx.compose.ui.window.ComposeUIViewController
import com.dmmeta.yatrago.core.di.initKoin
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    var isDarkMode =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(isDarkMode = isDarkMode)
}