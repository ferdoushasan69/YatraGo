package com.dmmeta.yatrago.core.di

import android.content.Context
import com.dmmeta.yatrago.core.platform.DataStoreFactory
import org.koin.dsl.module

actual val PlatformModule = module {
    single { DataStoreFactory(get<Context>()) }
}