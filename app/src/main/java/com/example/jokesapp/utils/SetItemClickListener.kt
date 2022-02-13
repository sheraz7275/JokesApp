package com.example.jokesapp.utils

import com.example.jokesapp.dataModels.Joke

interface SetItemClickListener {

    fun onItemClick(joke: Joke)

}