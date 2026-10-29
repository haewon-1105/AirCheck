package au.edu.jcu.assessment.utilityapp.di

import au.edu.jcu.assessment.utilityapp.data.AirQualityApi
import au.edu.jcu.assessment.utilityapp.data.AirQualityRepository
import au.edu.jcu.assessment.utilityapp.data.NetworkAirQualityRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Manual dependency injection container.
 * Provides shared dependencies for the application.
 */
interface AppContainer {
    val airQualityRepository: AirQualityRepository
}

class DefaultAppContainer : AppContainer {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val airQualityApi: AirQualityApi by lazy {
        retrofit.create(AirQualityApi::class.java)
    }

    override val airQualityRepository: AirQualityRepository by lazy {
        NetworkAirQualityRepository(airQualityApi)
    }

    companion object {
        private const val BASE_URL =
            "https://air-quality-api.open-meteo.com/"
    }
}
