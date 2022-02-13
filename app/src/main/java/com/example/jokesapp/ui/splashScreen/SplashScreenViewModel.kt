package com.example.jokesapp.ui.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesapp.repository.JokesRepository
import com.example.jokesapp.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val repository: SplashRepository)
    :ViewModel() {
    private val _appCounter = repository.appCounter
    val appCounter=_appCounter.asStateFlow()

    init {
        getAppCountData()
    }

    private fun getAppCountData(){
        viewModelScope.launch {
            repository.getAppCount()
        }
    }

     suspend fun setAppCountData(){

            repository.setAppCount()

    }

}