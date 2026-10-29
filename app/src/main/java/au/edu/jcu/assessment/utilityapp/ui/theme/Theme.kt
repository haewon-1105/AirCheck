
package au.edu.jcu.assessment.utilityapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AirCheckPurple,
    onPrimary = Color.White,
    onSurface = AirCheckDarkText,
    onSurfaceVariant = AirCheckSecondaryText,
    secondaryContainer = AirCheckSelectedCard,
    onSecondaryContainer = AirCheckDarkText,
    surface = Color.White,
    surfaceVariant = AirCheckDefaultCard,
    outlineVariant = AirCheckCardBorder,
    tertiary = AirCheckAirIcon
)

private val DarkColorScheme = darkColorScheme(
    primary = AirCheckDarkPrimary,
    onPrimary = AirCheckDarkBackground,
    background = AirCheckDarkBackground,
    surface = AirCheckDarkSurface,
    onSurface = AirCheckDarkOnSurface,
    onSurfaceVariant = AirCheckDarkOnSurfaceVariant,
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = AirCheckDarkOnSurface,
    surfaceVariant = Color(0xFF49454F),
    outlineVariant = Color(0xFF49454F),
    tertiary = Color(0xFFB4C5FF)
)

/**
 * Applies the AirCheck Material 3 color scheme and typography.
 */
@Composable
fun MobileTech_UtilityAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
