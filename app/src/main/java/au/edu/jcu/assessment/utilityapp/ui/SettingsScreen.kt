
package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.R
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
    val cities = listOf(
        Triple(City.Singapore, "Marina Bay", R.drawable.singapore_bg),
        Triple(City.Seoul, "N Seoul Tower", R.drawable.seoul_bg),
        Triple(City.Tokyo, "Tokyo Tower", R.drawable.tokyo_bg)
    )

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
            color = Color(0xFF10164F)
        )

        Text(
            text = "Choose your location",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF10164F)
        )

        Text(
            text = "Select a city to view its air quality.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF606580)
        )

        cities.forEach { (city, landmark, imageRes) ->
            CityCard(
                city = city.name,
                landmark = landmark,
                imageRes = imageRes,
                isSelected = selectedCity == city,
                onClick = { onCitySelected(city) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "AirCheck Settings Preview"
)
@Composable
fun SettingsScreenPreview() {
    MobileTech_UtilityAppTheme {
        SettingsScreen(
            selectedCity = City.Singapore,
            onCitySelected = {}
        )
    }
}
