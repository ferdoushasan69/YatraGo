package com.dmmeta.choloja.core.di

import app.cash.sqldelight.db.SqlDriver
import com.dmmeta.choloja.database.AppDatabase
import com.dmmeta.choloja.core.platform.DataStoreFactory
import com.dmmeta.choloja.data.local.AppDataSource
import com.dmmeta.choloja.data.repository.DatabaseRepositoryImpl
import com.dmmeta.choloja.db.DatabaseDriverFactory
import com.dmmeta.choloja.domain.repository.DatabaseRepository
import org.koin.dsl.module

val LocalModule = module {
    single { get<DataStoreFactory>().createDataStore() }
    single<SqlDriver> { get<DatabaseDriverFactory>().createDriver() }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }
    single { AppDataSource(get()) }
    single { AppDatabase(get()) }

}