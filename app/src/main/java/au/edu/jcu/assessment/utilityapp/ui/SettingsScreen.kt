package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import au.edu.jcu.assessment.utilityapp.data.City
import au.edu.jcu.assessment.utilityapp.ui.components.CityCard
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

/**
 * Displays available cities and allows the user to select one.
 */
@Composable
fun SettingsScreen(
    selectedCity: City,
    onCitySelected: (City) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Choose your location",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Select a city to view its air quality.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        City.all.forEach { city ->
            CityCard(
                city = city.name,
                landmark = city.landmark,
                imageRes = city.imageRes,
                isSelected = selectedCity == city,
                onClick = { onCitySelected(city) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "AirCheck Settings Light"
)
@Composable
private fun SettingsScreenLightPreview() {
    MobileTech_UtilityAppTheme(darkTheme = false) {
        SettingsScreen(
            selectedCity = City.Singapore,
            onCitySelected = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "AirCheck Settings Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SettingsScreenDarkPreview() {
    MobileTech_UtilityAppTheme(darkTheme = true) {
        SettingsScreen(
            selectedCity = City.Singapore,
            onCitySelected = {}
        )
    }
}
