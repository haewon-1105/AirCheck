package au.edu.jcu.assessment.utilityapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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

    private var fetchJob: Job? = null

    fun fetchAirQuality(selectedLocation: String) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            isLoading = true
            errorMessage = null
            airQuality = null

            try {
                airQuality = repository.getAirQuality(selectedLocation)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                errorMessage = when (e) {
                    is java.net.UnknownHostException,
                    is java.net.ConnectException,
                    is java.net.SocketTimeoutException ->
                        "Please check your internet connection and try again."

                    else -> "Unable to load air quality data. Please try again."
                }
            } finally {
                isLoading = false
            }
        }
    }
}