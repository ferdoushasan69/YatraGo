package com.dmmeta.choloja

import android.app.Application
import com.dmmeta.choloja.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@BaseApp)
            printLogger()
        }
    }
}