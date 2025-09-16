package com.dmmeta.nolapp.presentation

import android.os.Build
import com.dmmeta.nolapp.presentation.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()