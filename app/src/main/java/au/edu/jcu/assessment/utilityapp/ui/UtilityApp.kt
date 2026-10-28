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

@Composable
fun UtilityApp() {
    var selectedTab by rememberSaveable {
        mutableStateOf(AppTab.Utility)
    }
    var selectedLocationName by rememberSaveable {
        mutableStateOf(City.Singapore.name)
    }

    val selectedCity = City.fromName(selectedLocationName)

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
                AppTab.Utility -> UtilityScreen(selectedCity)

                AppTab.Settings -> SettingsScreen(
                    selectedLocation = selectedCity.name,
                    onLocationChange = { newLocation ->
                        selectedLocationName = newLocation
                    }
                )
            }
        }
    }
}
