package com.udacity.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    application: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(application) {

    init {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                mainRepository.nearEarth()
            }
        }
    }

    class Factory(
        private val application: Application,
        private val mainRepository: MainRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(application, mainRepository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}