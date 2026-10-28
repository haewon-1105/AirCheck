
package au.edu.jcu.assessment.utilityapp.ui

import au.edu.jcu.assessment.utilityapp.data.CurrentAirQuality

sealed interface AirQualityUiState {
    data object Loading : AirQualityUiState

    data class Success(
        val data: CurrentAirQuality
    ) : AirQualityUiState

    data class Error(
        val message: String
    ) : AirQualityUiState
}
