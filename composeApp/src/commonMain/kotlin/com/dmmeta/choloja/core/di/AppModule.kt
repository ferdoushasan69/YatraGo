package com.dmmeta.choloja.core.di

import org.koin.dsl.module

val AppModule = module {
    includes(viewModelModule, LocalModule,PlatformModule,UseCaseModule)
}
