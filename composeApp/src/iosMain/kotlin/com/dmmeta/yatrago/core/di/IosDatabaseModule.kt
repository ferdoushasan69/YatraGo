package com.dmmeta.yatrago.core.di

import com.dmmeta.yatrago.database.AppDatabase
import com.dmmeta.yatrago.data.local.AppDataSource
import com.dmmeta.yatrago.db.DatabaseDriverFactory

class IosDatabaseModule : DatabaseModule {
    private val db by lazy {
        AppDatabase(
            DatabaseDriverFactory().createDriver()
        )
    }

    override fun provideAppDataSource(): AppDataSource = AppDataSource(db)
}