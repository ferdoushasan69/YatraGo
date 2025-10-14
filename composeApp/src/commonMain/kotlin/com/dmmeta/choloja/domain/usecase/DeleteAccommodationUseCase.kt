package com.dmmeta.choloja.domain.usecase

import com.dmmeta.choloja.domain.repository.DatabaseRepository

class DeleteAccommodationUseCase(private val repository: DatabaseRepository) {

    suspend operator fun invoke(id: Long) = repository.delete(id = id)
}