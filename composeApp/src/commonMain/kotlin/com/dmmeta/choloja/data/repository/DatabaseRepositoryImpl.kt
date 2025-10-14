package com.dmmeta.choloja.data.repository

import com.dmmeta.choloja.data.local.AppDataSource
import com.dmmeta.choloja.domain.model.Accommodation
import com.dmmeta.choloja.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val appDataSource: AppDataSource
) : DatabaseRepository {
    override suspend fun insert(accommodation: Accommodation) {
        appDataSource.insert(
            id = accommodation.id,
            name = accommodation.name,
            stars = accommodation.stars.toLong(),
            address = accommodation.address,
            cityLine = accommodation.cityLine,
            photoUrl = accommodation.photoUrl,
            description = accommodation.description,
            facilities = accommodation.facilities,
            mainPolicy = accommodation.mainPolicy,
            importantInfo = accommodation.importantInfo,
            sellerName = accommodation.sellerName,
            priceKrw = accommodation.priceKrw,
            discount = accommodation.discount
        )
    }

    override fun getAll(): Flow<List<Accommodation>> = appDataSource.getAll()


    override suspend fun delete(id: Long) {
        appDataSource.delete(id)
    }


}