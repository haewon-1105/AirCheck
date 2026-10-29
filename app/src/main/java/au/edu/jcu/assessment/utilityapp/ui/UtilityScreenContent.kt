package au.edu.jcu.assessment.utilityapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.data.City
import au.edu.jcu.assessment.utilityapp.data.CurrentAirQuality
import au.edu.jcu.assessment.utilityapp.ui.components.AqiCard
import au.edu.jcu.assessment.utilityapp.ui.components.PollutantCard
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

/**
 * Displays air quality details, pollutant values, and a refresh button.
 *
 * Uses the selected city's background image.
 */
@Composable
fun UtilityScreenContent(
    city: City,
    uiState: AirQualityUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val airQuality = (uiState as? AirQualityUiState.Success)?.data
    val isLoading = uiState is AirQualityUiState.Loading

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(city.imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.55f
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.45f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "AirCheck",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "${city.name} Air Quality",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            AqiCard(uiState = uiState)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PollutantCard(
                    label = "PM2.5",
                    value = airQuality?.pm25,
                    modifier = Modifier.weight(1f)
                )

                PollutantCard(
                    label = "PM10",
                    value = airQuality?.pm10,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = onRefresh,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = if (isLoading) "Refreshing..." else "Refresh",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "AirCheck Content Light"
)
@Composable
private fun UtilityScreenContentLightPreview() {
    MobileTech_UtilityAppTheme(darkTheme = false) {
        UtilityScreenContent(
            city = City.Singapore,
            uiState = AirQualityUiState.Loading,
            onRefresh = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "AirCheck Content Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun UtilityScreenContentDarkPreview() {
    MobileTech_UtilityAppTheme(darkTheme = true) {
        UtilityScreenContent(
            city = City.Singapore,
            uiState = AirQualityUiState.Loading,
            onRefresh = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "AirCheck - Hazardous Dark",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun UtilityScreenContentHazardousDarkPreview() {
    MobileTech_UtilityAppTheme(darkTheme = true) {
        UtilityScreenContent(
            city = City.Singapore,
            uiState = AirQualityUiState.Success(
                CurrentAirQuality(
                    usAqi = 320,
                    pm25 = 150.0,
                    pm10 = 200.0
                )
            ),
            onRefresh = {}
        )
    }
}
