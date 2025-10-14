package com.dmmeta.choloja.domain.usecase

import com.dmmeta.choloja.domain.model.Accommodation
import com.dmmeta.choloja.domain.repository.DatabaseRepository

class InsertAccommodationUseCase(
    private val repository: DatabaseRepository

) {
    suspend operator fun invoke(
        accommodation: Accommodation
    ) {
        repository.insert(
            accommodation = accommodation
        )
    }

}