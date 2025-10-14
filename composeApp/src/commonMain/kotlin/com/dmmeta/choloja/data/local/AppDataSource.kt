package com.dmmeta.choloja.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.dmmeta.choloja.database.AppDatabase
import com.dmmeta.choloja.data.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map


class AppDataSource(db: AppDatabase) {
    private val query = db.accommodationEntityQueries

    suspend fun insert(
        id: Long? = null,
        name: String,
        stars: Long,
        address: String,
        cityLine: String,
        photoUrl: String,
        description: String,
        facilities: List<String>,
        mainPolicy: String,
        importantInfo: String,
        sellerName: String,
        priceKrw: String,
        discount: String

    ) {
        query.insert(
            id = id,
            name = name,
            stars = stars,
            address = address,
            cityLine = cityLine,
            photoUrl = photoUrl,
            description = description,
            facilities = facilities.toString(),
            mainPolicy = mainPolicy,
            importantInfo = importantInfo,
            sellerName = sellerName,
            priceKrw = priceKrw,
            discount = discount
        )
    }

    fun getAll() = query.getAll().asFlow().mapToList(Dispatchers.IO).map { list ->
        list.map {
            it
                .toDomain()
        }
    }

    suspend fun update(
        id: Long,
        name: String,
        location: String,
        discount_price: String,
        total_price: String
    ) {
        query.updateName(
            id = id,
            name = name,
        )
    }

    suspend fun delete(id: Long) = query.delete(id)
}