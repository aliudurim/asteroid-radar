package com.udacity.asteroidradar.ui.main

import com.udacity.asteroidradar.models.NearEarth
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.Resource
import com.udacity.asteroidradar.network.ResponseHandler

class MainRepository(private val responseHandler: ResponseHandler) {
    suspend fun nearEarth(): Resource<NearEarth> {
        return try {
            return responseHandler.handleSuccess(Network.service.nearEarth())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}