package com.dmmeta.choloja.core.di

import com.dmmeta.choloja.database.AppDatabase
import com.dmmeta.choloja.data.local.AppDataSource
import com.dmmeta.choloja.db.DatabaseDriverFactory

class IosDatabaseModule : DatabaseModule {
    private val db by lazy {
        AppDatabase(
            DatabaseDriverFactory().createDriver()
        )
    }

    override fun provideAppDataSource(): AppDataSource = AppDataSource(db)
}