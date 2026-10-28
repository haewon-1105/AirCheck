package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(val label: String, val icon: ImageVector) {
    Utility("Utility", Icons.Default.Home),
    Settings("Settings", Icons.Default.Settings)
}