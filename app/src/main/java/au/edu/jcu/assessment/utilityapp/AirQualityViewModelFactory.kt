
package au.edu.jcu.assessment.utilityapp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import au.edu.jcu.assessment.utilityapp.data.AirQualityRepository
import au.edu.jcu.assessment.utilityapp.ui.AirQualityViewModel

class AirQualityViewModelFactory(
    private val repository: AirQualityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(AirQualityViewModel::class.java)) {
            val savedStateHandle: SavedStateHandle =
                extras.createSavedStateHandle()

            @Suppress("UNCHECKED_CAST")
            return AirQualityViewModel(
                repository = repository,
                savedStateHandle = savedStateHandle
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
