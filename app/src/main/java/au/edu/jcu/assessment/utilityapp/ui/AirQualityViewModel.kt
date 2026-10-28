package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import au.edu.jcu.assessment.utilityapp.UtilityApplication
import au.edu.jcu.assessment.utilityapp.data.AirQualityRepository
import au.edu.jcu.assessment.utilityapp.data.City
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Manages air quality data, selected city, and loading or error states.
 *
 * The selected city is restored through SavedStateHandle.
 */
class AirQualityViewModel(
    private val repository: AirQualityRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf<AirQualityUiState>(
        AirQualityUiState.Loading
    )
        private set

    private var fetchJob: Job? = null

    var selectedCity by mutableStateOf(
        City.fromName(
            savedStateHandle[KEY_SELECTED_CITY] ?: City.Singapore.name
        )
    )
        private set

    init {
        refresh()
    }

    /**
     * Updates the selected city and loads its air quality data.
     */
    fun selectCity(city: City) {
        if (selectedCity == city) return

        selectedCity = city
        savedStateHandle[KEY_SELECTED_CITY] = city.name
        fetchAirQuality(city)
    }

    /**
     * Reloads air quality data for the currently selected city.
     */
    fun refresh() {
        fetchAirQuality(selectedCity)
    }

    /**
     * Cancels the previous request and fetches the latest air quality data.
     */
    private fun fetchAirQuality(city: City) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            uiState = AirQualityUiState.Loading

            try {
                val result = repository.getAirQuality(city)
                uiState = AirQualityUiState.Success(result)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = when (e) {
                    is IOException ->
                        "Please check your internet connection and try again."

                    else ->
                        "Unable to load air quality data. Please try again."
                }

                uiState = AirQualityUiState.Error(message)
            }
        }
    }

    companion object {
        private const val KEY_SELECTED_CITY = "selectedCity"

        /**
         * Creates the ViewModel with application dependencies and saved state.
         */
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as UtilityApplication

                AirQualityViewModel(
                    repository = app.container.airQualityRepository,
                    savedStateHandle = createSavedStateHandle()
                )
            }
        }
    }
}
