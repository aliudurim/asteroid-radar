package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.persistence.AsteroidRadarDatabase
import com.udacity.asteroidradar.ui.main.MainRepository
import com.udacity.asteroidradar.ui.utils.getCurrentDate
import com.udacity.asteroidradar.ui.utils.getDateAfterToday
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class AsteroidDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val database = AsteroidRadarDatabase.getInstance(applicationContext)
    private val mainRepository = MainRepository(database)

    override suspend fun doWork(): Result = coroutineScope {
        try {
            mainRepository.photoOfTheDay()
            mainRepository.nearEarth(getCurrentDate(), getDateAfterToday())
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "AsteroidDataWorker"
    }
}