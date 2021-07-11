package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidNetwork
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AsteroidRepository(private val database: AsteroidDatabase) {
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    @Throws(Throwable::class)
    suspend fun refreshAsteroidList() {
        val queryDate = getNextSevenDaysFormattedDates()
        withContext(Dispatchers.IO) {
            val response = AsteroidNetwork.service.getAsteroids(
                queryDate.first(),
                queryDate.last()
            )
            val jsonResponse = JSONObject(response)
            val parsedResponse = parseAsteroidsJsonResult(jsonResponse)
            database.asteroidDao.insertAll(*parsedResponse.asDatabaseModel())
        }
    }
}