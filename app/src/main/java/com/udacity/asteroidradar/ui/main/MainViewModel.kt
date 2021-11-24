package com.udacity.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    application: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(application) {

    private var _asteroids = MutableLiveData<Resource<List<Asteroid>>>()
    val asteroids: LiveData<Resource<List<Asteroid>>>
        get() = _asteroids

    private var _pictureOfTheDay = MutableLiveData<Resource<PictureOfDay>>()
    val pictureOfTheDay: LiveData<Resource<PictureOfDay>>
        get() = _pictureOfTheDay

    init {
        getAsteroids()
    }

    fun getAsteroids() {
        viewModelScope.launch {
            _asteroids.value = Resource.loading(null)
            val response = withContext(Dispatchers.IO) {
                mainRepository.nearEarth("2015-09-07", "2015-09-08")
            }
            val photoOfTheDay = withContext(Dispatchers.IO) {
                mainRepository.photoOfTheDay()
            }
            _asteroids.value = response
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