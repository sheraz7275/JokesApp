package com.example.jokesapp.dataModels

data class Flags(
    val explicit: Boolean?,
    val nsfw: Boolean?,
    val political: Boolean?,
    val racist: Boolean?,
    val religious: Boolean?,
    val sexist: Boolean?
)