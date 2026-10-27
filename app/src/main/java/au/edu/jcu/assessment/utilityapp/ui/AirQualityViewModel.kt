
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.data.AirQualityRepository
import au.edu.jcu.assessment.utilityapp.data.City
import au.edu.jcu.assessment.utilityapp.data.CurrentAirQuality
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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

    fun fetchAirQuality(city: City) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            isLoading = true
            errorMessage = null
            airQuality = null

            try {
                airQuality = repository.getAirQuality(city)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                errorMessage = when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is SocketTimeoutException ->
                        "Please check your internet connection and try again."

                    else ->
                        "Unable to load air quality data. Please try again."
                }
            } finally {
                isLoading = false
            }
        }
    }
}
