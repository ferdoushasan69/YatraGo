package com.dmmeta.yatrago.domain.usecase

import com.dmmeta.yatrago.domain.repository.DatabaseRepository

class DeleteAccommodationUseCase(private val repository: DatabaseRepository) {

    suspend operator fun invoke(id: Long) = repository.delete(id = id)
}