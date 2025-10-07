package com.dmmeta.yatrago.core.di

import android.content.Context
import com.dmmeta.yatrago.core.platform.DataStoreFactory
import com.dmmeta.yatrago.db.DatabaseDriverFactory
import org.koin.dsl.module

actual val PlatformModule = module {
    single { DataStoreFactory(get<Context>()) }
    single { DatabaseDriverFactory(get<Context>()) }
    single { AndroidDataBaseModule(get<Context>()) }
}