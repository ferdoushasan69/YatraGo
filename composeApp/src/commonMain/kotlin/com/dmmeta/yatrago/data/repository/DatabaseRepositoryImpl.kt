package com.dmmeta.yatrago.data.repository

import com.dmmeta.yatrago.data.local.AppDataSource
import com.dmmeta.yatrago.domain.model.Accommodation
import com.dmmeta.yatrago.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val appDataSource: AppDataSource
) : DatabaseRepository {
    override suspend fun insert(accommodation: Accommodation) {
        appDataSource.insert(
            id = accommodation.id,
            name = accommodation.name,
            location = accommodation.location,
            discount_price = accommodation.discountPrice,
            total_price = accommodation.totalPrice
        )
    }

    override fun getAll(): Flow<List<Accommodation>> = appDataSource.getAll()


    override suspend fun delete(id: Long) {
        appDataSource.delete(id)
    }


}