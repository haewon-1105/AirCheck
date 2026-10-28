package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Main app screen that manages tab navigation and connects
 * the ViewModel to the Utility and Settings screens.
 */
@Composable
fun UtilityApp() {
    val airQualityViewModel: AirQualityViewModel = viewModel(
        factory = AirQualityViewModel.Factory
    )

    var selectedTab by rememberSaveable {
        mutableStateOf(AppTab.Utility)
    }

    val selectedCity = airQualityViewModel.selectedCity

    Scaffold(
        bottomBar = {
            NavigationBar {
                AppTab.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label
                            )
                        },
                        label = { Text(tab.label) },
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            AppTab.Utility -> UtilityScreen(
                city = selectedCity,
                uiState = airQualityViewModel.uiState,
                onRefresh = airQualityViewModel::refresh,
                modifier = Modifier.padding(innerPadding)
            )

            AppTab.Settings -> SettingsScreen(
                selectedCity = selectedCity,
                onCitySelected = airQualityViewModel::selectCity,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
