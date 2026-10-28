
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.AirQualityViewModelFactory
import au.edu.jcu.assessment.utilityapp.R
import au.edu.jcu.assessment.utilityapp.UtilityApplication
import au.edu.jcu.assessment.utilityapp.data.City
import au.edu.jcu.assessment.utilityapp.ui.components.AqiCard
import au.edu.jcu.assessment.utilityapp.ui.components.PollutantCard

@Composable
fun UtilityScreen(selectedLocation: String) {
    val application = LocalContext.current.applicationContext as UtilityApplication

    val airQualityViewModel: AirQualityViewModel = viewModel(
        factory = AirQualityViewModelFactory(
            repository = application.container.airQualityRepository
        )
    )

    var refreshCount by remember { mutableIntStateOf(0) }
    var showErrorDialog by remember { mutableStateOf(true) }

    val uiState = airQualityViewModel.uiState
    val airQuality = (uiState as? AirQualityUiState.Success)?.data
    val isLoading = uiState is AirQualityUiState.Loading
    val errorMessage = (uiState as? AirQualityUiState.Error)?.message

    LaunchedEffect(selectedLocation, refreshCount) {
        val city = when (selectedLocation) {
            "Seoul" -> City.Seoul
            "Tokyo" -> City.Tokyo
            else -> City.Singapore
        }
        airQualityViewModel.fetchAirQuality(city)
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

            AqiCard(uiState = uiState)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PollutantCard(
                    label = "PM2.5",
                    value = airQuality?.pm25,
                    modifier = Modifier.weight(1f)
                )
                PollutantCard(
                    label = "PM10",
                    value = airQuality?.pm10,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { refreshCount++ },
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
        }

        if (showErrorDialog && errorMessage != null && !isLoading) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Unable to Load Data") },
                text = {
                    Text(errorMessage ?: "Unable to load air quality data.")
                },
                confirmButton = {
                    TextButton(
                        onClick = { refreshCount++ },
                        enabled = !isLoading
                    ) {
                        Text(if (isLoading) "Retrying..." else "Retry")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
