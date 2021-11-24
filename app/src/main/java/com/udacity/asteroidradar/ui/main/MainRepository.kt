package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.persistence.AsteroidRadarDatabase
import com.udacity.asteroidradar.ui.utils.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(
    private val database: AsteroidRadarDatabase
) {

    val asteroidList: LiveData<List<Asteroid>>
        get() = database.asteroidDao().getAll()

    val todayAsteroidList: LiveData<List<Asteroid>>
        get() = database.asteroidDao().getTodayAsteroid(getCurrentDate())

    val pictureOfDay: LiveData<PictureOfDay>
        get() = database.pictureDayDao().get()

    suspend fun nearEarth(startDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = Network.service.nearEarth(startDate, endDate)
                val list: MutableList<Asteroid> = mutableListOf()
                for (entry in response.nearEarthObjects.entries) {
                    for (asteroid in entry.value) {
                        list.add(
                            Asteroid(
                                asteroid.id, asteroid.codename,
                                entry.key, asteroid.absoluteMagnitude,
                                asteroid.estimatedDiameter.kilometers.estimatedDiameter,
                                asteroid.closeApproachDate[0].relativeVelocity.kilometersPerSecond,
                                asteroid.closeApproachDate[0].missDistance.astronomical,
                                asteroid.isPotentiallyHazardous
                            )
                        )
                    }
                }
                database.asteroidDao().updateData(list)
            } catch (e: Exception) {
            }
        }
    }

    suspend fun photoOfTheDay() {
        withContext(Dispatchers.IO) {
            try {
                val photoOfTheDay = Network.service.photoOfTheDay()
                database.pictureDayDao().updateData(photoOfTheDay)
            } catch (e: Exception) {
            }
        }
    }
}