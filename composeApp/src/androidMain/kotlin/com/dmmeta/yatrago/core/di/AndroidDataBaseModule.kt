package com.dmmeta.yatrago.core.di

import android.content.Context
import com.dmmeta.yatrago.PostDatabase
import com.dmmeta.yatrago.data.local.AppDataSource
import com.dmmeta.yatrago.db.DatabaseDriverFactory

class AndroidDataBaseModule(private val context: Context) : DatabaseModule {
    private val db by lazy {
        PostDatabase(
            DatabaseDriverFactory(context).createDriver()
        )
    }

    override fun provideAppDataSource(): AppDataSource = AppDataSource(db)
}