package au.edu.jcu.assessment.utilityapp.ui
import au.edu.jcu.assessment.utilityapp.data.City

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.AirQualityViewModelFactory
import au.edu.jcu.assessment.utilityapp.UtilityApplication

@Composable
fun UtilityApp() {
    val application = LocalContext.current.applicationContext as UtilityApplication

    val airQualityViewModel: AirQualityViewModel = viewModel(
        factory = AirQualityViewModelFactory(
            repository = application.container.airQualityRepository
        )
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
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                AppTab.Utility -> UtilityScreen(
                    selectedLocation = selectedCity,
                    airQualityViewModel = airQualityViewModel
                )

                AppTab.Settings -> SettingsScreen(
                    selectedLocation = selectedCity.name,
                    onLocationChange = { newLocation ->
                        airQualityViewModel.selectCity(City.fromName(newLocation))
                    }
                )
            }
        }
    }
}
