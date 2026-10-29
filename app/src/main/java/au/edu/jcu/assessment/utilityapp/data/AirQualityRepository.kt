package au.edu.jcu.assessment.utilityapp.data

/**
 * Defines how air quality data is retrieved.
 * Allows fake implementations for testing.
 */
interface AirQualityRepository {
    suspend fun getAirQuality(city: City): CurrentAirQuality
}

/**
 * Retrieves current air quality data from Open-Meteo.
 */
class NetworkAirQualityRepository(
    private val api: AirQualityApi
) : AirQualityRepository {

    override suspend fun getAirQuality(city: City): CurrentAirQuality {
        return api.getAirQuality(
            latitude = city.latitude,
            longitude = city.longitude
        ).current
    }
}
