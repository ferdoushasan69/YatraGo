package com.dmmeta.yatrago.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.dmmeta.yatrago.PostDatabase
import com.dmmeta.yatrago.data.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map


class AppDataSource(db: PostDatabase) {
    private val query = db.accommodationEntityQueries

    fun insert(
        id: Long?,
        name: String,
        location: String,
        discount_price: String,
        total_price: String
    ) {
        query.insert(
            id = id,
            name = name,
            location = location,
            discount_price = discount_price,
            total_price = total_price
        )
    }

    fun getAll() = query.getAll().asFlow().mapToList(Dispatchers.IO).map { list ->
        list.map {
            it
                .toDomain()
        }
    }

    fun update(
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

    fun delete(id: Long) = query.delete(id)
}