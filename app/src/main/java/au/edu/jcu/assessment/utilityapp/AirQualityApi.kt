package au.edu.jcu.assessment.utilityapp

import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {

    @GET("v1/air-quality")
    suspend fun getAirQuality(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "us_aqi,pm2_5,pm10"
    ): AirQualityResponse
}

data class AirQualityResponse(
    val current: CurrentAirQuality
)

data class CurrentAirQuality(
    val us_aqi: Int,
    val pm2_5: Double,
    val pm10: Double
)