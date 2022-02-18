package com.example.jokesapp.repository

import com.example.jokesapp.dataModels.Joke
import com.example.jokesapp.network.RestApi
import com.example.jokesapp.utils.ApiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class JokesRepository @Inject constructor(private val restApi: RestApi) {

    var jokesList = MutableStateFlow<List<Joke>?>(null)
    val status = MutableStateFlow<String>(ApiStatus.Loading.toString())
    /*
    only have network source  no offline caching.
      we can also setup room data base here
    */
    suspend fun getJokes() {
        status.value = ApiStatus.Loading.toString()
        try {
            val resp = restApi.getJokesListAsync().await()
            jokesList.value = resp.jokes
            status.value = ApiStatus.Success.toString()

        } catch (exe: Exception) {
            status.value = ApiStatus.Error.toString()

        }
    }
}