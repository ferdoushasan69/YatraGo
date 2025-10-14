package com.dmmeta.choloja.core.di

import com.dmmeta.choloja.presentation.screen.accommodation_details.AccommodationDetailsViewModel
import com.dmmeta.choloja.presentation.screen.cart.CartViewModel
import com.dmmeta.choloja.presentation.screen.category_search.CategorySearchViewModel
import com.dmmeta.choloja.presentation.screen.map.SharedMapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategorySearchViewModel() }
    viewModel { CartViewModel(get()) }
    viewModel { AccommodationDetailsViewModel(get()) }
    viewModel { SharedMapViewModel() }
}
