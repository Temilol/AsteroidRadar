package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.AsteroidNetwork
import com.udacity.asteroidradar.data.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyImageRepository {
    private val _dailyImage = MutableLiveData<PictureOfDay>()
    val dailyImage: LiveData<PictureOfDay>
        get() = _dailyImage

    @Throws(Throwable::class)
    suspend fun refreshDailyImage() {
        withContext(Dispatchers.IO){
            val response = AsteroidNetwork.service.getDailyImage()
            _dailyImage.postValue(response)
        }
    }
}