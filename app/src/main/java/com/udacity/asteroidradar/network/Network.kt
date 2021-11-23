package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val logging = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val client = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(1, TimeUnit.MINUTES)
    .writeTimeout(1, TimeUnit.MINUTES)
    .addInterceptor(logging)

/**
 * Use the Retrofit builder to build a retrofit object using a Gson converter with our Gson
 * object.
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client.build())
    .build()

/**
 * A public Network object that exposes the lazy-initialized Retrofit service
 */
object Network {
    private val TAG: String = Network::class.java.simpleName

    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }
}