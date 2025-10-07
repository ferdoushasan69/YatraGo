package com.dmmeta.yatrago.core.di

import com.dmmeta.yatrago.data.local.AppDataSource

interface DatabaseModule {
    fun provideAppDataSource() : AppDataSource
}