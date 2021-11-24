package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.Resource
import com.udacity.asteroidradar.network.ResponseHandler
import com.udacity.asteroidradar.persistence.AsteroidRadarDatabase
import com.udacity.asteroidradar.ui.utils.getCurrentDate

class MainRepository(
    private val responseHandler: ResponseHandler,
    private val database: AsteroidRadarDatabase
) {

    val asteroidList: LiveData<List<Asteroid>>
        get() = database.asteroidDao().getAll()

    val todayAsteroidList: LiveData<List<Asteroid>>
        get() = database.asteroidDao().getTodayAsteroid(getCurrentDate())

    suspend fun nearEarth(startDate: String, endDate: String): Resource<List<Asteroid>> {
        return try {
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
            return responseHandler.handleSuccess(list)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun photoOfTheDay(): Resource<PictureOfDay> {
        return try {
            return responseHandler.handleSuccess(Network.service.photoOfTheDay())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}