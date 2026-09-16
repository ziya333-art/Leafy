package com.ujizin.leafy.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.leafy.domain.model.DailyWeather
import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.model.Plant
import com.ujizin.leafy.domain.result.filterNotLoading
import com.ujizin.leafy.domain.result.getOrNull
import com.ujizin.leafy.domain.result.mapResult
import com.ujizin.leafy.domain.usecase.location.load.LoadGardenLocationUseCase
import com.ujizin.leafy.domain.usecase.plant.load.LoadAllPlantUseCase
import com.ujizin.leafy.domain.usecase.weather.load.LoadForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadAllPlant: LoadAllPlantUseCase,
    private val loadGardenLocation: LoadGardenLocationUseCase,
    private val loadForecast: LoadForecastUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var todayWeather: DailyWeather? = null

    fun loadHome() {
        loadWeather()
        loadAllPlant()
            .mapResult()
            .catch { throwable -> _uiState.update { HomeUIState.Error(throwable) } }
            .onEach { plants -> _uiState.update { HomeUIState.Success(plants, todayWeather) } }
            .launchIn(viewModelScope)
    }

    private fun loadWeather() {
        loadGardenLocation()
            .filterNotLoading()
            .map { it.getOrNull() }
            .onEach { location -> if (location != null) loadForecastFor(location) }
            .launchIn(viewModelScope)
    }

    private fun loadForecastFor(location: GardenLocation) {
        loadForecast(location, days = 1)
            .filterNotLoading()
            .map { it.getOrNull() }
            .onEach { forecast ->
                todayWeather = forecast?.firstOrNull()
                _uiState.update { state ->
                    if (state is HomeUIState.Success) state.copy(weather = todayWeather) else state
                }
            }
            .launchIn(viewModelScope)
    }
}

sealed interface HomeUIState {
    @Immutable
    data class Success(val plants: List<Plant>, val weather: DailyWeather? = null) : HomeUIState

    @Immutable
    data class Error(val throwable: Throwable?) : HomeUIState

    @Immutable
    data object Loading : HomeUIState
}
