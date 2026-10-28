
package au.edu.jcu.assessment.utilityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.assessment.utilityapp.ui.AirQualityUiState
import au.edu.jcu.assessment.utilityapp.ui.AqiLevel

/**
 * Displays the US AQI value, air quality category,
 * health advice, and data loading status.
 */
@Composable
fun AqiCard(
    uiState: AirQualityUiState,
    modifier: Modifier = Modifier
) {
    val airQuality = (uiState as? AirQualityUiState.Success)?.data
    val isLoading = uiState is AirQualityUiState.Loading
    val errorMessage = (uiState as? AirQualityUiState.Error)?.message

    val aqiLevel = airQuality?.usAqi?.let { AqiLevel.fromAqi(it) }
    val aqiColor = aqiLevel?.color ?: MaterialTheme.colorScheme.onSurface

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.22f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "US AQI:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF303040)
            )

            Text(
                text = when {
                    isLoading -> "Loading..."
                    airQuality != null -> "${airQuality.usAqi}"
                    else -> "--"
                },
                style = MaterialTheme.typography.displayLarge,
                fontSize = if (airQuality == null) 28.sp else 56.sp,
                color = aqiColor
            )
        }

        Text(
            text = aqiLevel?.label
                ?: if (isLoading) "Loading..." else "Unavailable",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = aqiColor
        )

        if (aqiLevel != null) {
            Text(
                text = aqiLevel.advice,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF303040)
            )
        }

        Text(
            text = when {
                isLoading -> "Fetching air quality data..."
                errorMessage != null -> errorMessage
                airQuality != null -> "Latest available data from Open-Meteo"
                else -> "No air quality data available"
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}
