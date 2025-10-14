package com.dmmeta.choloja.core.di

import com.dmmeta.choloja.data.local.AppDataSource

interface DatabaseModule {
    fun provideAppDataSource() : AppDataSource
}