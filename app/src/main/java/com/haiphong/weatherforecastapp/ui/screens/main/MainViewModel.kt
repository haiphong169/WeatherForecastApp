package com.haiphong.weatherforecastapp.ui.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.weatherforecastapp.data.DataOrException
import com.haiphong.weatherforecastapp.data.Favorite
import com.haiphong.weatherforecastapp.model.Weather
import com.haiphong.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val openSearch: Boolean = false,
    val searchCity: String = "Hanoi",
    val lastCity: String = "Hanoi",
    val favoriteList: List<Favorite> = listOf(),
    val isFavorite: Boolean = false,
)

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    val data: MutableState<DataOrException<Weather, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, null))

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getWeather(city = uiState.value.searchCity)
        getFavorites()
        checkIfFavorite()
    }

    fun openSearchBar() {
        _uiState.update {
            it.copy(
                openSearch = true
            )
        }
    }

    fun onSearchCityChange(newString: String) {
        _uiState.update {
            it.copy(
                searchCity = newString
            )
        }
    }

    fun submitSearch() {
        if (uiState.value.searchCity != uiState.value.lastCity) {
            getWeather(city = uiState.value.searchCity)
            _uiState.update {
                it.copy(
                    lastCity = uiState.value.searchCity
                )
            }
        }
        _uiState.update {
            it.copy(
                openSearch = false
            )
        }
    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(city = city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        onSearchCityChange(city)
        checkIfFavorite()
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }

    fun flush() {
        viewModelScope.launch {
            repository.flush()
        }
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { favList ->
                _uiState.update {
                    it.copy(
                        favoriteList = favList
                    )
                }
            }
        }
    }

    private fun checkIfFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkIfFavorite(uiState.value.searchCity).distinctUntilChanged()
                .collect { isFavorite ->
                    Log.d("isFavorite", isFavorite.toString())
                    _uiState.update {
                        it.copy(
                            isFavorite = isFavorite
                        )
                    }
                }
        }
    }
}