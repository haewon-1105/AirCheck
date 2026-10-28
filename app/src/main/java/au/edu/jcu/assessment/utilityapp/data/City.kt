package au.edu.jcu.assessment.utilityapp.data

import androidx.annotation.DrawableRes
import au.edu.jcu.assessment.utilityapp.R

/**
 * Represents a city available for air quality monitoring.
 * Stores its name, landmark, coordinates, and background image.
 */
data class City(
    val name: String,
    val landmark: String,
    val latitude: Double,
    val longitude: Double,
    @DrawableRes val imageRes: Int
) {
    companion object {
        val Singapore = City(
            "Singapore", "Marina Bay",
            1.3521, 103.8198,
            R.drawable.singapore_bg
        )

        val Seoul = City(
            "Seoul", "N Seoul Tower",
            37.5665, 126.9780,
            R.drawable.seoul_bg
        )

        val Tokyo = City(
            "Tokyo", "Tokyo Tower",
            35.6762, 139.6503,
            R.drawable.tokyo_bg
        )

        val all: List<City> = listOf(Singapore, Seoul, Tokyo)

        fun fromName(name: String): City {
            return all.firstOrNull { it.name == name } ?: Singapore
        }
    }
}