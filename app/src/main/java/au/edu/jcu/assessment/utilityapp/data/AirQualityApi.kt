package au.edu.jcu.assessment.utilityapp.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API for retrieving current air quality data from Open-Meteo.
 */
interface AirQualityApi {

    @GET("v1/air-quality")
    suspend fun getAirQuality(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT_FIELDS
    ): AirQualityResponse

    companion object {
        private const val CURRENT_FIELDS = "us_aqi,pm2_5,pm10"
    }
}

/**
 * Response containing current air quality measurements.
 */
data class AirQualityResponse(
    val current: CurrentAirQuality
)

/**
 * Current air quality values returned by Open-Meteo.
 */
data class CurrentAirQuality(
    @SerializedName("us_aqi")
    val usAqi: Int,

    @SerializedName("pm2_5")
    val pm25: Double,

    @SerializedName("pm10")
    val pm10: Double
)
