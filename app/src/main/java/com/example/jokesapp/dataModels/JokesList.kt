package com.example.jokesapp.dataModels

data class JokesList(
    val amount: Int?,
    val error: Boolean?,
    val jokes: List<Joke>?
)