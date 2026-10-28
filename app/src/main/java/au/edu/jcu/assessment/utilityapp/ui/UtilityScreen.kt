
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.AirQualityViewModelFactory
import au.edu.jcu.assessment.utilityapp.UtilityApplication
import au.edu.jcu.assessment.utilityapp.data.City

@Composable
fun UtilityScreen(selectedLocation: City) {
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
        airQualityViewModel.fetchAirQuality(selectedLocation)
    }

    LaunchedEffect(errorMessage, isLoading) {
        if (errorMessage != null) {
            showErrorDialog = true
        } else if (!isLoading && airQuality != null) {
            showErrorDialog = false
        }
    }

    UtilityScreenContent(
        selectedLocation = selectedLocation.name,
        uiState = uiState,
        onRefresh = { refreshCount++ }
    )

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
