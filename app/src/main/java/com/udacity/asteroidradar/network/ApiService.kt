package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.models.NearEarth
import com.udacity.asteroidradar.models.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("neo/rest/v1/neo/browse")
    suspend fun nearEarth(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): NearEarth

    @GET("planetary/apod")
    suspend fun photoOfTheDay(@Query("api_key") apiKey: String = BuildConfig.API_KEY): PictureOfDay
}