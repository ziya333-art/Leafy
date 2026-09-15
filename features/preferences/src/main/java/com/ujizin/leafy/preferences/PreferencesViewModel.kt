package com.ujizin.leafy.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.model.User
import com.ujizin.leafy.domain.result.filterNotLoading
import com.ujizin.leafy.domain.result.getOrNull
import com.ujizin.leafy.domain.usecase.location.load.LoadGardenLocationUseCase
import com.ujizin.leafy.domain.usecase.location.save.SaveGardenLocationUseCase
import com.ujizin.leafy.domain.usecase.user.update.UpdateUserUseCase
import com.ujizin.leafy.domain.usecase.weather.geocode.GeocodeLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val updateUser: UpdateUserUseCase,
    loadGardenLocation: LoadGardenLocationUseCase,
    private val geocodeLocation: GeocodeLocationUseCase,
    private val saveGardenLocation: SaveGardenLocationUseCase,
) : ViewModel() {

    val gardenLocation: StateFlow<GardenLocation?> = loadGardenLocation()
        .filterNotLoading()
        .map { it.getOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialValue = null)

    private val _locationSearchFailed = MutableStateFlow(false)
    val locationSearchFailed: StateFlow<Boolean> = _locationSearchFailed.asStateFlow()

    fun update(user: User) {
        updateUser(user).launchIn(viewModelScope)
    }

    fun saveLocation(city: String) {
        geocodeLocation(city)
            .filterNotLoading()
            .map { it.getOrNull() }
            .onEach { location ->
                _locationSearchFailed.value = location == null
                if (location != null) {
                    saveGardenLocation(location).launchIn(viewModelScope)
                }
            }
            .launchIn(viewModelScope)
    }
}
