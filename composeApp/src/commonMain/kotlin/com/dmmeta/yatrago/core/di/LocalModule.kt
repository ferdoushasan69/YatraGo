package com.dmmeta.yatrago.core.di

import app.cash.sqldelight.db.SqlDriver
import com.dmmeta.yatrago.PostDatabase
import com.dmmeta.yatrago.core.platform.DataStoreFactory
import com.dmmeta.yatrago.data.local.AppDataSource
import com.dmmeta.yatrago.data.repository.DatabaseRepositoryImpl
import com.dmmeta.yatrago.db.DatabaseDriverFactory
import com.dmmeta.yatrago.domain.repository.DatabaseRepository
import org.koin.dsl.module

val LocalModule = module {
    single { get<DataStoreFactory>().createDataStore() }
    single<SqlDriver> { get<DatabaseDriverFactory>().createDriver() }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }
    single { AppDataSource(get()) }
    single { PostDatabase(get()) }

}