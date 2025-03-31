import org.gradle.kotlin.dsl.test

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.trackforce.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.trackforce.weatherapp"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Google Maps Compose
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)

    // ExoPlayer (Media3)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer)

    // Hilt para inyección de dependencias
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
//    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    implementation(libs.hilt.android.gradle.plugin)

    // Retrofit + Gson para consumo de API
    implementation(libs.retrofit)
    implementation(libs.gson)

    implementation(libs.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.play.services.location)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.secrets.gradle.plugin)
    implementation(libs.retrofit.gson)
    implementation(libs.androidx.security.crypto)

    testImplementation(libs.junit)

}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}
