package com.dmmeta.choloja.presentation.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmmeta.choloja.domain.model.Accommodation
import com.dmmeta.choloja.domain.usecase.GetAllDataUseCase
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