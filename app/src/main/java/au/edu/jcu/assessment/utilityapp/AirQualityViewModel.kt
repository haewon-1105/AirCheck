package au.edu.jcu.assessment.utilityapp

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AirQualityViewModel(
    private val repository: AirQualityRepository
) : ViewModel() {

    var airQuality by mutableStateOf<CurrentAirQuality?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchAirQuality(selectedLocation: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null


            try {
                airQuality = repository.getAirQuality(selectedLocation)


            } catch (e: Exception) {
                airQuality = null
                errorMessage = "Failed to load air quality data"
            } finally {
                isLoading = false
            }
        }
    }
}