package com.dmmeta.yatrago.core.di

import com.dmmeta.yatrago.presentation.screen.accommodation_details.AccommodationDetailsViewModel
import com.dmmeta.yatrago.presentation.screen.cart.CartViewModel
import com.dmmeta.yatrago.presentation.screen.category_search.CategorySearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategorySearchViewModel() }
    viewModel { CartViewModel(get()) }
    viewModel { AccommodationDetailsViewModel(get()) }
}
