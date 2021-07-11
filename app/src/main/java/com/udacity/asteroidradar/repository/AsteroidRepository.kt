package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidNetwork
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AsteroidRepository(private val database: AsteroidDatabase) {
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    @Throws(Throwable::class)
    suspend fun refreshAsteroidList() {
        val todayDate = getCurrentDate()
        withContext(Dispatchers.IO) {
            val response = AsteroidNetwork.service.getAsteroids(
                todayDate,
                todayDate
            )
            val jsonResponse = JSONObject(response)
            val parsedResponse = parseAsteroidsJsonResult(jsonResponse)
            database.asteroidDao.insertAll(*parsedResponse.asDatabaseModel())
        }
    }

    private fun getCurrentDate (): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }
}