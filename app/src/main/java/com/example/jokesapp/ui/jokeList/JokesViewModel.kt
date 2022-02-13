package com.example.jokesapp.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesapp.repository.JokesRepository
import com.example.jokesapp.utils.ApiStatus.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(private val jokesRepository: JokesRepository) :
    ViewModel() {

    // data encapsulation
    private val _jokesList = jokesRepository.jokesList
    val jokesList = _jokesList.asStateFlow()

    private val _apiError = MutableSharedFlow<String>()
    val apiError = _apiError.asSharedFlow()

    private val _apiSuccess = MutableSharedFlow<String>()
    val apiSuccess = _apiSuccess.asSharedFlow()

    private val status = jokesRepository.status

    init {
        apiCall()
        observeApiStatus()
    }

    fun apiCall() {
        viewModelScope.launch { jokesRepository.getJokes() }
    }

    private fun observeApiStatus() {

        viewModelScope.launch {
            status.collect {
                when (it) {
                    Success.name -> {
                        _apiSuccess.emit(it)
                    }
                    Error.name -> {
                        _apiError.emit(it)
                    }
                }
            }
        }
    }
}