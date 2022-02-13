package com.example.jokesapp.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.jokesapp.utils.DataStoreKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = DataStoreKeys.APP_COUNT.name)

    private val appCount = intPreferencesKey(DataStoreKeys.COUNTER.name)

    // function responsible for writing data in memory
    suspend fun save() {
        context.dataStore.edit { settings ->
            val currentCounterValue = settings[appCount] ?: 0
            settings[appCount] = currentCounterValue + 1
        }
    }

    // function responsible for reading data from in memory
    suspend fun read(): Flow<Int> {
        val counterFlow: Flow<Int> = context.dataStore.data
            .map { preferences ->

                preferences[appCount] ?: 0
            }
        return counterFlow
    }
}