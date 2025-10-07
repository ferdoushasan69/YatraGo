package com.dmmeta.yatrago.presentation.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmmeta.yatrago.domain.model.Accommodation
import com.dmmeta.yatrago.domain.usecase.GetAllDataUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CartViewModel(
    private val getAllDataUseCase: GetAllDataUseCase
): ViewModel() {

    val items: StateFlow<List<Accommodation>> = getAllDataUseCase().stateIn(
        scope = viewModelScope,
        SharingStarted.Eagerly, emptyList()
    )
}