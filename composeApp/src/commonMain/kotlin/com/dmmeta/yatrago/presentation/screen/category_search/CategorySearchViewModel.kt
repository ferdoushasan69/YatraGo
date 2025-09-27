package com.dmmeta.yatrago.presentation.screen.category_search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CategorySearchViewModel : ViewModel() {

    private val _recentSearch = mutableStateListOf<LocationItem>()
    val recentSearch : List<LocationItem> get() = _recentSearch

    fun onSearch(query : String,locations: List<LocationItem>){

        val filter = locations.firstOrNull{
            it.location.contains(query,ignoreCase = true)
        }

        filter?.let {
            _recentSearch.remove(it)

            _recentSearch.add(0,it)

            if (_recentSearch.size >  3){
                _recentSearch.removeAt(_recentSearch.lastIndex)
            }
        }


    }

    fun removeSearch(location: LocationItem){
        _recentSearch.remove(location)
    }
}