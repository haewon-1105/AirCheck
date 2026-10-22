package au.edu.jcu.assessment.utilityapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AirQualityViewModelFactory(
    private val repository: AirQualityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AirQualityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AirQualityViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}