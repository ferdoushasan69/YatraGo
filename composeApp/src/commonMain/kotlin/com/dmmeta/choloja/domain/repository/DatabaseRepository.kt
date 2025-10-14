package com.dmmeta.choloja.domain.repository

import com.dmmeta.choloja.domain.model.Accommodation
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insert(accommodation: Accommodation)

    fun getAll(): Flow<List<Accommodation>>

    suspend fun delete(id: Long)
}