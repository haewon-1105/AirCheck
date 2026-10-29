
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import au.edu.jcu.assessment.utilityapp.data.City
import au.edu.jcu.assessment.utilityapp.data.CurrentAirQuality
import au.edu.jcu.assessment.utilityapp.ui.components.ErrorDialog
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

/**
 * Displays air quality information and handles refresh and error actions.
 */
@Composable
fun UtilityScreen(
    city: City,
    uiState: AirQualityUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    var errorDismissed by remember(uiState) {
        mutableStateOf(false)
    }

    UtilityScreenContent(
        city = city,
        uiState = uiState,
        onRefresh = onRefresh,
        modifier = modifier
    )

    if (uiState is AirQualityUiState.Error && !errorDismissed) {
        ErrorDialog(
            message = uiState.message,
            onRetry = onRefresh,
            onDismiss = { errorDismissed = true }
        )
    }
}

/**
 * Preview of the loading state.
 */
@Preview(
    showBackground = true,
    name = "AirCheck - Loading"
)
@Composable
private fun UtilityScreenLoadingPreview() {
    MobileTech_UtilityAppTheme {
        UtilityScreen(
            city = City.Singapore,
            uiState = AirQualityUiState.Loading,
            onRefresh = {}
        )
    }
}

/**
 * Preview of a successful air quality response.
 */
@Preview(
    showBackground = true,
    name = "AirCheck - Success"
)
@Composable
private fun UtilityScreenSuccessPreview() {
    MobileTech_UtilityAppTheme {
        UtilityScreen(
            city = City.Singapore,
            uiState = AirQualityUiState.Success(
                CurrentAirQuality(
                    usAqi = 75,
                    pm25 = 15.3,
                    pm10 = 16.6
                )
            ),
            onRefresh = {}
        )
    }
}

/**
 * Preview of the error state.
 */
@Preview(
    showBackground = true,
    name = "AirCheck - Error"
)
@Composable
private fun UtilityScreenErrorPreview() {
    MobileTech_UtilityAppTheme {
        UtilityScreen(
            city = City.Singapore,
            uiState = AirQualityUiState.Error(
                "Please check your internet connection and try again."
            ),
            onRefresh = {}
        )
    }
}
