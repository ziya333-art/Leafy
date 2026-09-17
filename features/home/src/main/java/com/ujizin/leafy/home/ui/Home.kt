package com.ujizin.leafy.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ujizin.leafy.core.themes.LeafyTheme
import com.ujizin.leafy.core.ui.annotation.ThemePreviews
import com.ujizin.leafy.core.ui.extensions.OnClick
import com.ujizin.leafy.core.ui.local.LocalUser
import com.ujizin.leafy.home.HomeUIState
import com.ujizin.leafy.home.HomeViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onTakePictureClick: OnClick,
    onDrawerClick: OnClick,
    onSearchClick: OnClick,
    onPlantClick: (id: Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) { viewModel.loadHome() }

    var showGuide by remember { mutableStateOf(false) }
    var showDoctor by remember { mutableStateOf(false) }

    HomeContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        nickname = LocalUser.current.nickname,
        onTakePictureClick = onTakePictureClick,
        onSearchClick = onSearchClick,
        onDrawerClick = onDrawerClick,
        onPlantClick = onPlantClick,
        onGuideClick = { showGuide = true },
        onDoctorClick = { showDoctor = true },
    )

    if (showGuide) {
        PlantGuide(onDismiss = { showGuide = false })
    }

    if (showDoctor) {
        PlantDoctor(onDismiss = { showDoctor = false })
    }
}

@Composable
private fun HomeContent(
    state: HomeUIState,
    nickname: String,
    onTakePictureClick: OnClick,
    onSearchClick: OnClick,
    onDrawerClick: OnClick,
    modifier: Modifier = Modifier,
    onPlantClick: (Long) -> Unit,
    onGuideClick: OnClick = {},
    onDoctorClick: OnClick = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        when (val result: HomeUIState = state) {
            HomeUIState.Loading -> {}
            is HomeUIState.Success -> HomeSection(
                nickname = nickname,
                plants = result.plants,
                weather = result.weather,
                onEmptyPlantClick = onTakePictureClick,
                onSearchClick = onSearchClick,
                onDrawerClick = onDrawerClick,
                onPlantClick = onPlantClick,
                onGuideClick = onGuideClick,
                onDoctorClick = onDoctorClick,
            )

            is HomeUIState.Error -> {}
        }
    }
}

@ThemePreviews
@Composable
private fun HomeContentPreview() {
    LeafyTheme {
        Surface {
            HomeContent(
                state = HomeUIState.Success(listOf()),
                nickname = "User",
                onTakePictureClick = {},
                onSearchClick = {},
                onDrawerClick = {},
                onPlantClick = {},
                onGuideClick = {},
                onDoctorClick = {},
            )
        }
    }
}
