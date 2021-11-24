package com.udacity.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    application: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(application) {

    val asteroids = mainRepository.asteroidList

    private var _pictureOfTheDay = MutableLiveData<Resource<PictureOfDay>>()
    val pictureOfTheDay: LiveData<Resource<PictureOfDay>>
        get() = _pictureOfTheDay

    val showLoader = MutableLiveData<Boolean>().apply { value = false }

    init {
        getAsteroidsAndPictureOfTheDay()
    }

    fun getAsteroidsAndPictureOfTheDay() {
        viewModelScope.launch {
            showLoader.value = true
            withContext(Dispatchers.IO) {
                mainRepository.nearEarth("2015-09-07", "2015-09-08")
                mainRepository.photoOfTheDay()
            }
            showLoader.value = false
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