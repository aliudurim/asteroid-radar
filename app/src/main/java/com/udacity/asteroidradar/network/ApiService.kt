package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.models.NearEarth
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("neo/rest/v1/neo/browse")
    suspend fun nearEarth(@Query("api_key") apiKey: String = BuildConfig.API_KEY): NearEarth
}