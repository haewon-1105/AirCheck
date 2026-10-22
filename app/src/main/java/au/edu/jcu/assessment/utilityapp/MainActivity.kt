package au.edu.jcu.assessment.utilityapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.LaunchedEffect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

val airQualityApi: AirQualityApi = Retrofit.Builder()
    .baseUrl("https://air-quality-api.open-meteo.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(AirQualityApi::class.java)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTech_UtilityAppTheme {
                UtilityApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    MobileTech_UtilityAppTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Utility") }
    var selectedLocation by remember { mutableStateOf("Singapore") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            when (selectedTab) {
                "Utility" -> UtilityScreen(selectedLocation)

                "Settings" -> SettingsScreen(
                    selectedLocation = selectedLocation,
                    onLocationChange = { newLocation ->
                        selectedLocation = newLocation
                    }
                )
            }

        }
        }
    }



@Composable
fun UtilityScreen(
    selectedLocation: String,
    airQualityViewModel: AirQualityViewModel = viewModel(
        factory = AirQualityViewModelFactory(
            repository = AirQualityRepository(airQualityApi)
        )
    )
) {
    var refreshCount by remember { mutableIntStateOf(0) }
    val airQuality = airQualityViewModel.airQuality
    val isLoading = airQualityViewModel.isLoading
    val errorMessage = airQualityViewModel.errorMessage

    LaunchedEffect(selectedLocation, refreshCount) {
        airQualityViewModel.fetchAirQuality(selectedLocation)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("AirCheck SG", style = MaterialTheme.typography.headlineMedium)
        Text(
            "$selectedLocation Air Quality",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = when {
                isLoading -> "Loading..."
                errorMessage != null -> "Unavailable"
                airQuality != null -> "US AQI: ${airQuality?.us_aqi}"
                else -> "No data"
            },
            style = MaterialTheme.typography.displayLarge,
            color = Color(0xFF2E7D32)
        )

        Text(
            text = when (airQuality?.us_aqi) {
                null -> if (isLoading) "Loading..." else "Unavailable"
                in 0..50 -> "Good"
                in 51..100 -> "Moderate"
                in 101..150 -> "Unhealthy for Sensitive Groups"
                in 151..200 -> "Unhealthy"
                in 201..300 -> "Very Unhealthy"
                else -> "Hazardous"
            },
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = when {
                isLoading -> "Fetching air quality data..."
                errorMessage != null -> errorMessage ?: "Unable to fetch data"
                airQuality != null -> "Latest available data from Open-Meteo"
                else -> "No air quality data available"
            },
            style = MaterialTheme.typography.bodySmall
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("PM2.5")
                    Text(
                        text = airQuality?.pm2_5?.let { "$it µg/m³" } ?: "--",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("PM10")
                    Text(
                        text = airQuality?.pm10?.let { "$it µg/m³" } ?: "--",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }



        Button(
            onClick = {
                refreshCount++
            },
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Refreshing..." else "Refresh")
        }

    }
}

@Composable
fun SettingsScreen(
    selectedLocation: String,
    onLocationChange: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp), Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings Screen", style = MaterialTheme.typography.headlineMedium)
        Text("This is where you can add toggles or preferences.")

        // Location selection
        Text("Selected Location: $selectedLocation")

        Button(onClick = { onLocationChange("Singapore") }) {
            Text("Singapore")
        }

        Button(onClick = { onLocationChange("Seoul") }) {
            Text("Seoul")
        }

        Button(onClick = { onLocationChange("Tokyo") }) {
            Text("Tokyo")
        }
    }
}
