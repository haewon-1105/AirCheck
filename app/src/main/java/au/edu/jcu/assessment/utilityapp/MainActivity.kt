package au.edu.jcu.assessment.utilityapp

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import retrofit2.Retrofit
import androidx.compose.ui.draw.clip
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Air
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
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
    var showErrorDialog by remember { mutableStateOf(true) }
    val airQuality = airQualityViewModel.airQuality
    val isLoading = airQualityViewModel.isLoading
    val errorMessage = airQualityViewModel.errorMessage

    LaunchedEffect(selectedLocation, refreshCount) {
        airQualityViewModel.fetchAirQuality(selectedLocation)
    }

    LaunchedEffect(errorMessage, isLoading) {
        if (errorMessage != null) {
            showErrorDialog = true
        } else if (!isLoading && airQuality != null) {
            showErrorDialog = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(
                when (selectedLocation) {
                    "Seoul" -> R.drawable.seoul_bg
                    "Tokyo" -> R.drawable.tokyo_bg
                    else -> R.drawable.singapore_bg
                }
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.55f
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "AirCheck",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF10164F)
        )

        Text(
            text = "$selectedLocation Air Quality",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF10164F)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.22f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

        val aqiColor = when (airQuality?.us_aqi) {
            null -> MaterialTheme.colorScheme.onSurface
            in 0..50 -> Color(0xFF2E7D32)
            in 51..100 -> Color(0xFFB8860B)
            in 101..150 -> Color(0xFFEF6C00)
            in 151..200 -> Color(0xFFD96B6B)
            in 201..300 -> Color(0xFF7B1FA2)
            else -> Color(0xFF7E0023)
        }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "US AQI:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF303040)
                )

                Text(
                    text = when {
                        isLoading -> "Loading..."
                        errorMessage != null -> "--"
                        airQuality != null -> "${airQuality.us_aqi}"
                        else -> "--"
                    },
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = if (airQuality == null) 28.sp else 56.sp,
                    color = aqiColor
                )
            }

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
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = aqiColor
        )

        Text(
            text = when {
                isLoading -> "Fetching air quality data..."
                errorMessage != null -> errorMessage ?: "Unable to fetch data"
                airQuality != null -> "Latest available data from Open-Meteo"
                else -> "No air quality data available"
            },
            style = MaterialTheme.typography.bodySmall
        )} // AQI Card


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf(
                "PM2.5" to airQuality?.pm2_5,
                "PM10" to airQuality?.pm10
            ).forEach { (label, value) ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = Color.White.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            imageVector = Icons.Default.Air,
                            contentDescription = null,
                            tint = Color(0xFF8EA8EF),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Text(
                        text = value?.let { "$it µg/m³" } ?: "--",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF10164F)
                    )
                }
            }
        }



        Button(
            onClick = {
                refreshCount++
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6657A6)
            )
        ) {
            Text(
                text = if (isLoading) "Refreshing..." else "Refresh",
                style = MaterialTheme.typography.titleMedium
            )
        }

    } // Column 끝

        if (showErrorDialog && (errorMessage != null || isLoading)) {
            AlertDialog(
                onDismissRequest = {
                    showErrorDialog = false
                },
                title = {
                    Text("Unable to Load Data")
                },
                text = {
                    Text(
                        if (isLoading) {
                            "Trying to reconnect..."
                        } else {
                            errorMessage ?: "Unable to load air quality data."
                        }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            refreshCount++
                        },
                        enabled = !isLoading
                    ) {
                        Text(if (isLoading) "Retrying..." else "Retry")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showErrorDialog = false
                        }
                    ) {
                        Text("Close")
                    }
                }
            )
        }

    } // Box 끝
} // UtilityScreen 끝

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
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF10164F)
        )

        Text(
            text = "Choose your location",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF10164F)
        )

        Text(
            text = "Select a city to view its air quality.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF606580)
        )

        Card(
            onClick = { onLocationChange("Singapore") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (selectedLocation == "Singapore")
                    Color(0xFFEDE8FF)
                else
                    Color(0xFFF7F5FC)
            ),
            border = BorderStroke(
                width = if (selectedLocation == "Singapore") 2.dp else 1.dp,
                color = if (selectedLocation == "Singapore")
                    Color(0xFF7665C9)
                else
                    Color(0xFFE0DCEB)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Singapore",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10164F)
                    )

                    Text(
                        text = "Marina Bay",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF606580)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.singapore_bg),
                        contentDescription = "Marina Bay, Singapore",
                        modifier = Modifier
                            .size(width = 72.dp, height = 40.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    if (selectedLocation == "Singapore") {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = Color(0xFF7665C9),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        listOf(
            "Seoul" to "N Seoul Tower",
            "Tokyo" to "Tokyo Tower"
        ).forEach { (city, landmark) ->

            Card(
                onClick = { onLocationChange(city) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedLocation == city)
                        Color(0xFFEDE8FF)
                    else
                        Color(0xFFF7F5FC)
                ),
                border = BorderStroke(
                    width = if (selectedLocation == city) 2.dp else 1.dp,
                    color = if (selectedLocation == city)
                        Color(0xFF7665C9)
                    else
                        Color(0xFFE0DCEB)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = city,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10164F)
                        )

                        Text(
                            text = landmark,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF606580)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                when (city) {
                                    "Seoul" -> R.drawable.seoul_bg
                                    "Tokyo" -> R.drawable.tokyo_bg
                                    else -> R.drawable.singapore_bg
                                }
                            ),
                            contentDescription = "$city landmark",
                            modifier = Modifier
                                .size(width = 72.dp, height = 40.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        if (selectedLocation == city) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Selected",
                                tint = Color(0xFF7665C9),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
