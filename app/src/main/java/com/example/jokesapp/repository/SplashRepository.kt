package com.example.jokesapp.repository

import com.example.jokesapp.dataModels.Joke
import com.example.jokesapp.dataStore.PreferenceDataStore
import com.example.jokesapp.network.RestApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SplashRepository @Inject constructor(private val dataStore: PreferenceDataStore) {
    var appCounter = MutableStateFlow<Int>(0)

    suspend fun getAppCount() {
        dataStore.read().collect {
            appCounter.value = it
        }
    }

    suspend fun setAppCount() {
        dataStore.save()
    }
}