package com.udacity.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.ui.utils.getCurrentDate
import com.udacity.asteroidradar.ui.utils.getDateAfterToday
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(application) {

    private val weekAsteroidList = mainRepository.asteroidList
    private val todayAsteroidList = mainRepository.todayAsteroidList

    val pictureOfTheDay = mainRepository.pictureOfDay

    val showLoader = MutableLiveData<Boolean>().apply { value = false }

    val asteroidList: MediatorLiveData<List<Asteroid>> = MediatorLiveData()

    init {
        getAsteroidsAndPictureOfTheDay()
    }

    fun getAsteroidsAndPictureOfTheDay() {
        viewModelScope.launch {
            showLoader.value = true
            mainRepository.nearEarth(getCurrentDate(), getDateAfterToday())
            mainRepository.photoOfTheDay()
            showLoader.value = false
            asteroidList.addSource(weekAsteroidList) {
                asteroidList.value = it
            }
        }
    }

    fun onTodayAsteroidsClicked() {
        removeSource()
        asteroidList.addSource(todayAsteroidList) {
            asteroidList.value = it
        }
    }

    fun onViewWeekAsteroidsClicked() {
        removeSource()
        asteroidList.addSource(weekAsteroidList) {
            asteroidList.value = it
        }

    }

    fun onSavedAsteroidsClicked() {
        removeSource()
        asteroidList.addSource(weekAsteroidList) {
            asteroidList.value = it
        }
    }

    private fun removeSource() {
        asteroidList.removeSource(todayAsteroidList)
        asteroidList.removeSource(weekAsteroidList)
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