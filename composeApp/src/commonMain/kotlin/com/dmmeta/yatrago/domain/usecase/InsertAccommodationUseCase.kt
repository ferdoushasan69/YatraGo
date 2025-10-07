package com.dmmeta.yatrago.domain.usecase

import com.dmmeta.yatrago.domain.model.Accommodation
import com.dmmeta.yatrago.domain.repository.DatabaseRepository

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