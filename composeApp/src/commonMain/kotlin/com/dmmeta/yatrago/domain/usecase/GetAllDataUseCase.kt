package com.dmmeta.yatrago.domain.usecase

import com.dmmeta.yatrago.domain.repository.DatabaseRepository

class GetAllDataUseCase(private val repository: DatabaseRepository) {
     operator fun invoke() = repository.getAll()
}