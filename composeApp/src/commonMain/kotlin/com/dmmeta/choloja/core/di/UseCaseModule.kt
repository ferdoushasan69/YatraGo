package com.dmmeta.choloja.core.di

import com.dmmeta.choloja.domain.usecase.DeleteAccommodationUseCase
import com.dmmeta.choloja.domain.usecase.GetAllDataUseCase
import com.dmmeta.choloja.domain.usecase.InsertAccommodationUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    factory { InsertAccommodationUseCase(get()) }
    factory { DeleteAccommodationUseCase(get()) }
    factory { GetAllDataUseCase(get()) }

}