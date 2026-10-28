
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.ui.graphics.Color

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

    companion object {
        fun fromAqi(aqi: Int): AqiLevel = when {
            aqi <= 50 -> Good
            aqi <= 100 -> Moderate
            aqi <= 150 -> UnhealthySensitive
            aqi <= 200 -> Unhealthy
            aqi <= 300 -> VeryUnhealthy
            else -> Hazardous
        }
    }
}
