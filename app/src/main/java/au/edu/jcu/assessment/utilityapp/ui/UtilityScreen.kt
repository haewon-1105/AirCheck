
package au.edu.jcu.assessment.utilityapp.ui


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import au.edu.jcu.assessment.utilityapp.data.City
import au.edu.jcu.assessment.utilityapp.ui.components.ErrorDialog

@Composable
fun UtilityScreen(
    selectedLocation: City,
    airQualityViewModel: AirQualityViewModel
) {
    val uiState = airQualityViewModel.uiState

    var errorDismissed by remember(uiState) {
        mutableStateOf(false)
    }


    UtilityScreenContent(
        selectedLocation = selectedLocation.name,
        uiState = uiState,
        onRefresh = { airQualityViewModel.refresh() }
    )

    if (uiState is AirQualityUiState.Error && !errorDismissed) {
        ErrorDialog(
            message = uiState.message,
            onRetry = { airQualityViewModel.refresh() },
            onDismiss = { errorDismissed = true }
        )
    }
}
