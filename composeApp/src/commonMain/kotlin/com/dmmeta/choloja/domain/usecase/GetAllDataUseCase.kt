package com.dmmeta.choloja.domain.usecase

import com.dmmeta.choloja.domain.repository.DatabaseRepository

class GetAllDataUseCase(private val repository: DatabaseRepository) {
     operator fun invoke() = repository.getAll()
}