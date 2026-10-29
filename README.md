# AirCheck – Air Quality Monitoring App

AirCheck is an Android utility application developed for **CP3406 Assessment 1: Utility App** at James Cook University.

The app allows users to check current air quality information for selected cities and provides health guidance based on the US Air Quality Index (AQI).

## Features

- **Air Quality Monitoring:** Displays the latest available US AQI data.
- **City Selection:** Switch between Singapore, Seoul, and Tokyo.
- **Pollutant Information:** Displays PM2.5 and PM10 concentrations.
- **Health Guidance:** Provides advice based on six US AQI categories.
- **Refresh:** Allows users to request updated air quality information.
- **Error Handling:** Displays an error dialog with retry and close options when data cannot be loaded.
- **Light and Dark Themes:** Supports system light and dark modes using Material Design 3.
- **City Backgrounds:** Displays a different background image for each supported city.
- **State Restoration:** Preserves the selected city through Android's SavedStateHandle.

## Technologies Used

| Technology | Purpose |
|---|---|
| Kotlin | Android application development |
| Jetpack Compose | Declarative user interface |
| Material Design 3 | UI components and theming |
| ViewModel | UI state management |
| SavedStateHandle | Selected city state restoration |
| Kotlin Coroutines | Asynchronous API requests |
| Retrofit | HTTP API communication |
| Open-Meteo Air Quality API | Air quality data source |

## Data Source

AirCheck uses the [Open-Meteo Air Quality API](https://open-meteo.com/en/docs/air-quality-api) to retrieve air quality information.

The application requests:

- US Air Quality Index (`usAqi`)
- PM2.5 (`pm2_5`)
- PM10 (`pm10`)

Data is retrieved using the latitude and longitude of the selected city.

The information shown represents the latest available API data and should not be treated as a substitute for official emergency or medical advice.

## Supported Cities

| City | Landmark |
|---|---|
| Singapore | Marina Bay |
| Seoul | N Seoul Tower |
| Tokyo | Tokyo Tower |

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/haewon-1105/AirCheck.git
   ```

2. Open the project in Android Studio.
3. Allow Gradle to synchronize.
4. Select an Android emulator or compatible physical device.
5. Run the `app` configuration.
6. Ensure an internet connection is available to retrieve air quality data.

## How to Use

1. Open AirCheck to view air quality information for the selected city.
2. Review the US AQI, air quality category, pollutant values, and health guidance.
3. Tap **Settings** to select Singapore, Seoul, or Tokyo.
4. Return to **Utility** to view the selected city's air quality.
5. Tap **Refresh** to request updated information.

## Application Architecture

AirCheck separates its UI, state management, and data access responsibilities.

- **UtilityApp:** Manages bottom navigation between Utility and Settings.
- **UtilityScreen / UtilityScreenContent:** Displays air quality information and refresh controls.
- **SettingsScreen:** Allows users to select a city.
- **AirQualityViewModel:** Manages the selected city, loading states, API requests, and errors.
- **AirQualityRepository:** Retrieves air quality information.
- **AirQualityApi:** Defines the Retrofit API request.
- **City:** Centralizes city names, landmarks, coordinates, and background images.
- **AqiLevel:** Maps US AQI values to categories, indicator colors, and health advice.

## Testing

The application was manually tested using an Android emulator.

The following functionality was checked:

- Air quality data loading
- Switching between all three supported cities
- Updating city backgrounds and displayed data
- Navigation between Utility and Settings
- Refreshing air quality information
- Light and dark theme appearance
- Network error handling
- Retry and Close actions
- Successful project build

## Project Context

This application was developed as part of **CP3406 Assessment 1** for educational purposes.

The project builds on the provided Android utility app starter template and extends it with real API integration, state management, city selection, health guidance, and responsive Material Design 3 UI.
