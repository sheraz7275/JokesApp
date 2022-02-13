package com.example.jokesapp.network

import com.example.jokesapp.dataModels.JokesList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RestApi {

    @GET("Any?amount=10")
    fun getJokesListAsync(): Deferred<JokesList>

}

