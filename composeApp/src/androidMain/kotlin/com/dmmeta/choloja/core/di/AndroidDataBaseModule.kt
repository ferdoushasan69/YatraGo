package com.dmmeta.choloja.core.di

import android.content.Context
import com.dmmeta.choloja.data.local.AppDataSource
import com.dmmeta.choloja.database.AppDatabase
import com.dmmeta.choloja.db.DatabaseDriverFactory

class AndroidDataBaseModule(private val context: Context) : DatabaseModule {
    private val db by lazy {
        AppDatabase(
            DatabaseDriverFactory(context).createDriver()
        )
    }

    override fun provideAppDataSource(): AppDataSource = AppDataSource(db)
}