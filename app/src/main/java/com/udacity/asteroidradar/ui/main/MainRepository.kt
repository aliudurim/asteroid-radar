package com.udacity.asteroidradar.ui.main

import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.Resource
import com.udacity.asteroidradar.network.ResponseHandler

class MainRepository(private val responseHandler: ResponseHandler) {
    suspend fun nearEarth(): Resource<List<Asteroid>> {
        return try {
            val response = Network.service.nearEarth()
            return responseHandler.handleSuccess(response.nearEarthObjects.map {
                Asteroid(
                    it.id, it.codename,
                    "", it.absoluteMagnitude,
                    it.estimatedDiameter.kilometers.estimatedDiameter,
                    it.closeApproachDate[0].relativeVelocity.kilometersPerSecond,
                    it.closeApproachDate[0].missDistance.astronomical,
                    it.isPotentiallyHazardous
                )
            })
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