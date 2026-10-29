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
| Gson Converter | JSON response deserialization |
| Manual Dependency Injection | Dependency management through AppContainer |
| Open-Meteo Air Quality API | Air quality data source |

## Data Source

AirCheck uses the [Open-Meteo Air Quality API](https://open-meteo.com/en/docs/air-quality-api) to retrieve air quality information.

The application requests:

- US Air Quality Index (`us_aqi`)
- PM2.5 (`pm2_5`)
- PM10 (`pm10`)

Data is retrieved using the latitude and longitude of the selected city.

The information shown represents the latest available API data and should not be treated as a substitute for official emergency or medical advice.

### Attribution

Air quality data by [Open-Meteo.com](https://open-meteo.com/).

Data is provided by Open-Meteo and its underlying data providers. See the [Open-Meteo Terms](https://open-meteo.com/en/terms) for attribution and licensing information.

## Supported Cities

| City | Landmark |
|---|---|
| Singapore | Marina Bay |
| Seoul | N Seoul Tower |
| Tokyo | Tokyo Tower |

## How to Run

### Requirements

- Android Studio with support for Android SDK 36
- Android SDK 36 (compileSdk 36)
- Android device or emulator running Android 8.0 (API 26) or higher
- Internet connection for retrieving air quality data

### Installation

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

AirCheck follows an MVVM-inspired architecture with separate UI, state management, and data access layers. It uses manual dependency injection to connect these layers.

### Data Flow

```text
UtilityApplication
    └── AppContainer (Dependency Injection)
          └── NetworkAirQualityRepository
                └── AirQualityApi (Retrofit)
                      └── Open-Meteo API

UtilityApp
    ├── UtilityScreen
    │     └── UtilityScreenContent
    └── SettingsScreen
          │
          ▼
AirQualityViewModel
    ├── Selected city (SavedStateHandle)
    ├── AirQualityUiState
    │     ├── Loading
    │     ├── Success
    │     └── Error
    └── AirQualityRepository (interface)
          └── NetworkAirQualityRepository
```

### Main Components

- **UtilityApplication:** Initializes the application-level dependency container.
- **AppContainer:** Provides application dependencies using manual dependency injection.
- **UtilityApp:** Manages navigation between Utility and Settings.
- **UtilityScreen / UtilityScreenContent:** Displays air quality information and refresh controls.
- **SettingsScreen:** Allows users to select a city.
- **AirQualityViewModel:** Manages the selected city, API requests, loading states, and errors.
- **AirQualityUiState:** Represents Loading, Success, and Error states.
- **AirQualityRepository:** Defines the data access interface.
- **NetworkAirQualityRepository:** Implements the repository using the network API.
- **AirQualityApi:** Defines Retrofit API requests to Open-Meteo.
- **City:** Centralizes city names, landmarks, coordinates, and background images.
- **AqiLevel:** Maps AQI values to categories, colors, and health guidance.

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

## Known Limitations

- **Limited Cities:** The application currently supports only Singapore, Seoul, and Tokyo.
- **No GPS Integration:** Users select a city manually instead of using their current location.
- **Internet Required:** Air quality information cannot be retrieved without an internet connection.
- **No Automated Tests:** Testing has been performed manually using an Android emulator.
- **API Dependency:** Data availability and update frequency depend on the Open-Meteo Air Quality API.

## Project Context

This application was developed as part of **CP3406 Assessment 1** for educational purposes.

The project builds on the provided Android utility app starter template and extends it with real API integration, state management, city selection, health guidance, and responsive Material Design 3 UI.
