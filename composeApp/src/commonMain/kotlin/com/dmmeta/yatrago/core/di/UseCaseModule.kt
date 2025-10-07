package com.dmmeta.yatrago.core.di

import com.dmmeta.yatrago.domain.usecase.DeleteAccommodationUseCase
import com.dmmeta.yatrago.domain.usecase.GetAllDataUseCase
import com.dmmeta.yatrago.domain.usecase.InsertAccommodationUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    factory { InsertAccommodationUseCase(get()) }
    factory { DeleteAccommodationUseCase(get()) }
    factory { GetAllDataUseCase(get()) }

}