package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.DailyImageRepository
import retrofit2.HttpException

class BackgroundWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val asteroidRepository by lazy { AsteroidRepository(database) }
        val dailyImageRepository by lazy { DailyImageRepository() }
        return try {
            asteroidRepository.refreshAsteroidList()
            dailyImageRepository.refreshDailyImage()
            Result.success()
        } catch (e: HttpException) {
            Log.e(TASK_NAME, "Background Task Failure", e)
            Result.retry()
        }
    }

    companion object {
        const val TASK_NAME = "RefreshDataWorker"
    }
}