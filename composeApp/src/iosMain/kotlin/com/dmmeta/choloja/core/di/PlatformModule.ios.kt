package com.dmmeta.choloja.core.di

import com.dmmeta.choloja.core.platform.DataStoreFactory
import com.dmmeta.choloja.db.DatabaseDriverFactory
import org.koin.dsl.module

actual val PlatformModule = module {
    single{ DataStoreFactory() }
    single { IosDatabaseModule() }
    single { DatabaseDriverFactory() }
}