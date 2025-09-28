package com.dmmeta.yatrago.presentation.screen.category_search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CategorySearchViewModel : ViewModel() {

    private val _recentSearch = mutableStateListOf<LocationItem>()
    val recentSearch: List<LocationItem> get() = _recentSearch

    private val _filterResult = mutableStateListOf<LocationItem>()
    val filterResult: List<LocationItem> get() = _filterResult

    var searchQuery by mutableStateOf("")
        private set

    var isSearchEmpty by mutableStateOf(false)
        private set

    fun onSearch(query: String, locations: List<LocationItem>) {

        searchQuery = query

        if (query.isBlank()) {
            _filterResult.clear()
            isSearchEmpty = false
            return
        }


        val filter = locations.filter {
            it.location.contains(query, ignoreCase = true)
        }.take(3)

        _filterResult.clear()
        _filterResult.addAll(filter)
        isSearchEmpty = filter.isEmpty()

        if (filter.isNotEmpty()) {
            addRecentSearch(filter.first())
        }

    }

    fun addRecentSearch(location: LocationItem) {
        _recentSearch.remove(location)
        _recentSearch.add(0, location)

        if (_recentSearch.size > 3) {
            _recentSearch.removeAt(recentSearch.lastIndex)
        }
    }

    fun removeSearch(location: LocationItem) {
        _recentSearch.remove(location)
    }

    fun clearSearch() {
        searchQuery = ""
        _filterResult.clear()
        isSearchEmpty = false
    }
}