package com.dmmeta.choloja.db

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.dmmeta.choloja.database.AppDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(AppDatabase.Schema.synchronous(), "post2.db")
}