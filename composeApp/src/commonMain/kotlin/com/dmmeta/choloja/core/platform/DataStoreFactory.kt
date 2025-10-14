package com.dmmeta.choloja.core.platform

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect class DataStoreFactory {
    fun createDataStore(): DataStore<Preferences>
}