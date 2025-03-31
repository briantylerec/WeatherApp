package com.trackforce.weatherapp.di

import com.trackforce.weatherapp.preferences.SecurePreferences
import android.content.Context
import com.trackforce.weatherapp.data.remote.WeatherApi
import com.trackforce.weatherapp.data.repository.WeatherRepositoryImpl
import com.trackforce.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/3.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideSecurePreferences(@ApplicationContext context: Context): SecurePreferences {
        return SecurePreferences(context)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        securePreferences: SecurePreferences,
        context: Context
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, securePreferences, context)
    }
}
