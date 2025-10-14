package com.dmmeta.choloja.core.di

import android.content.Context
import com.dmmeta.choloja.core.platform.DataStoreFactory
import com.dmmeta.choloja.db.DatabaseDriverFactory
import org.koin.dsl.module

actual val PlatformModule = module {
    single { DataStoreFactory(get<Context>()) }
    single { DatabaseDriverFactory(get<Context>()) }
    single { AndroidDataBaseModule(get<Context>()) }
}