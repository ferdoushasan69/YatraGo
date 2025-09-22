package com.dmmeta.nolapp.core.di

import android.content.Context
import com.dmmeta.nolapp.core.platform.DataStoreFactory
import org.koin.dsl.module

actual val PlatformModule = module {
    single { DataStoreFactory(get<Context>()) }
}