package com.example.jokesapp.di

import com.example.jokesapp.dataStore.PreferenceDataStore
import com.example.jokesapp.network.RestApi
import com.example.jokesapp.repository.JokesRepository
import com.example.jokesapp.repository.SplashRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JokesModule {

    private const val BaseURL = "https://v2.jokeapi.dev/joke/"

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(restApi: RestApi): JokesRepository = JokesRepository(restApi)

    @Singleton
    @Provides
    fun provideSplashRepository(dataStore: PreferenceDataStore): SplashRepository =
        SplashRepository(dataStore)

}