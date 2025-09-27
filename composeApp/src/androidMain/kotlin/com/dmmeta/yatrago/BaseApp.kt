package com.dmmeta.yatrago

import android.app.Application
import com.dmmeta.yatrago.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@BaseApp)
        }
    }
}