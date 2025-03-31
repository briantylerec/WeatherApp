# Trackforce Mobile Challenge

## Description

The goal of this challenge is to create a simple mobile application that displays the weather information for the week based on the device's location. We used the **OpenWeatherMap API** for this.

### Requirements:
- **MVVM (Model-View-ViewModel)** architecture for structuring the app mixed with Clean Architecture.
- **Dagger Hilt** for dependency injection (optional, but recommended).
- **Offline capabilities**: The fetched data should be saved locally for offline usage.

### API used:
- [OpenWeatherMap API](https://openweathermap.org/api)
  - Register and obtain an API key to access the weather data.
- [Google Maps SDK API](https://developers.google.com/maps/documentation/android-sdk/overview?section=start&hl=es_419)
  - Register and obtain an API key to access the map.
---

## Features

1. **Get current weather data**:
   - The weather information is fetched through the OpenWeatherMap API based on the device's location.
   
2. **Local storage**:
   - The weather data is stored locally using SharedPreferences) to be accessible even when the device is offline.

3. **Offline mode**:
   - If there is no internet connection, the app will display the most recently stored data.

4. **UI Design**:
   - The app has a simple and user-friendly design, displaying current temperature, weather description, and other important details such as sunrise and sunset times.

5. **Dependency Injection**:
   - **Dagger Hilt** is used for managing the dependency injection to simplify the creation of necessary classes.

---

## Design patterns

1. **Creational Pattern: Factory and Singleton**
   - We use the Factory Method to centralize the creation of complex instances, such as the configuration of Retrofit and its services. Additionally, the Singleton pattern is applied to ensure that instances of classes like Retrofit service or repositories are created only once and reused throughout the app.

2. **Structural Pattern: Adapter**
   - The Adapter Pattern is implicitly used when transforming data between different layers of the application. For example, converting the data from the OpenWeatherMap API to the models used in the app via methods like toDomain().

3. **Behavioral Pattern: Observer**
   - The Observer Pattern is applied in managing state in the MVVM architecture. The ViewModel acts as the "Subject" and UI components (like WeatherScreen) observe changes in the data using LiveData or StateFlow, reacting to changes in weather data and updating the UI accordingly.

---

## Technologies Used

- **Kotlin**
- **Jetpack Compose**: For building the user interface.
- **MVVM (Model-View-ViewModel)**: For app structure.
- **Dagger Hilt**: For dependency injection.
- **SharedPreferences**: For storing weather data locally.

---

## Unit and UI tests

---

## Running the Application

### Prerequisites

1. **Android Studio**: Make sure you have the latest version of [Android Studio](https://developer.android.com/studio) installed.
2. **Enable Developer Mode on your Android device**:
   - Go to **Settings** > **About Phone**.
   - Tap **Build Number** 7 times to unlock developer options.
   - In **Developer Options**, enable **USB Debugging**.

3. **Get an API Key from OpenWeatherMap**:
   - Sign up on [OpenWeatherMap](https://openweathermap.org/api) and get your free API key.
   - Create a `strings.xml` file in your resource folder to store the API key.

   ```xml
   <string name="api_key">YOUR_API_KEY_HERE</string>
