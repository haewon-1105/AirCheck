package au.edu.jcu.assessment.utilityapp

class AirQualityRepository(
    private val api: AirQualityApi
) {
    suspend fun getAirQuality(
        selectedLocation: String
    ): CurrentAirQuality {

        val (latitude, longitude) = when (selectedLocation) {
            "Seoul" -> 37.5665 to 126.9780
            "Tokyo" -> 35.6762 to 139.6503
            else -> 1.3521 to 103.8198
        }

        return api.getAirQuality(
            latitude = latitude,
            longitude = longitude
        ).current
    }
}