package com.dmmeta.yatrago.presentation.screen.accommodation_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmmeta.yatrago.domain.model.Accommodation
import com.dmmeta.yatrago.domain.usecase.InsertAccommodationUseCase
import kotlinx.coroutines.launch

class AccommodationDetailsViewModel(
    private val insertAccommodationUseCase: InsertAccommodationUseCase,
) : ViewModel() {


    fun insert(accommodation: Accommodation){
        viewModelScope.launch {
            insertAccommodationUseCase(accommodation)
        }
    }
}