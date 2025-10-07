package com.dmmeta.yatrago.core.di

import com.dmmeta.yatrago.core.platform.DataStoreFactory
import com.dmmeta.yatrago.db.DatabaseDriverFactory
import org.koin.dsl.module

actual val PlatformModule = module {
    single{ DataStoreFactory() }
    single { IosDatabaseModule() }
    single { DatabaseDriverFactory() }
}