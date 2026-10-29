
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.ui.graphics.Color

/**
 * Represents US Air Quality Index (AQI) categories.
 *
 * Each category provides a display label, an indicator color,
 * and health advice based on the AQI level.
 */
enum class AqiLevel(
    val label: String,
    val color: Color,
    val advice: String
) {
    Good(
        "Good",
        Color(0xFF2E7D32),
        "Air quality is satisfactory."
    ),
    Moderate(
        "Moderate",
        Color(0xFFB8860B),
        "Unusually sensitive people should consider reducing prolonged outdoor exertion."
    ),
    UnhealthySensitive(
        "Unhealthy for Sensitive Groups",
        Color(0xFFEF6C00),
        "Sensitive groups should reduce prolonged outdoor exertion."
    ),
    Unhealthy(
        "Unhealthy",
        Color(0xFFD96B6B),
        "Everyone should reduce prolonged outdoor exertion."
    ),
    VeryUnhealthy(
        "Very Unhealthy",
        Color(0xFF7B1FA2),
        "Everyone should avoid prolonged outdoor exertion."
    ),
    Hazardous(
        "Hazardous",
        Color(0xFF7E0023),
        "Everyone should avoid outdoor physical activity."
    );

    /**
     * Returns a brighter AQI indicator color for dark mode.
     */
    fun displayColor(isDarkTheme: Boolean): Color {
        if (!isDarkTheme) return color

        return when (this) {
            Good -> Color(0xFF81C784)
            Moderate -> Color(0xFFFFD54F)
            UnhealthySensitive -> Color(0xFFFFB74D)
            Unhealthy -> Color(0xFFEF9A9A)
            VeryUnhealthy -> Color(0xFFCE93D8)
            Hazardous -> Color(0xFFFF8A80)
        }
    }

    companion object {
        /**
         * Returns the corresponding AQI category.
         *
         * Negative values are treated as zero as a defensive fallback.
         */
        fun fromAqi(aqi: Int): AqiLevel {
            val safeAqi = aqi.coerceAtLeast(0)

            return when {
                safeAqi <= 50 -> Good
                safeAqi <= 100 -> Moderate
                safeAqi <= 150 -> UnhealthySensitive
                safeAqi <= 200 -> Unhealthy
                safeAqi <= 300 -> VeryUnhealthy
                else -> Hazardous
            }
        }
    }
}
