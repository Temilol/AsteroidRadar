package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.DailyImageRepository
import com.udacity.asteroidradar.util.AsyncOperationState
import kotlinx.coroutines.launch

class MainViewModel(application: Application, context: Context) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidRepository by lazy { AsteroidRepository(database) }
    private val dailyImageRepository by lazy { DailyImageRepository() }

    val asteroidList: LiveData<List<Asteroid>> = asteroidRepository.asteroids
    val dailyImage: LiveData<PictureOfDay> = dailyImageRepository.dailyImage
    private val _state = MutableLiveData<AsyncOperationState>()
    val state: LiveData<AsyncOperationState>
        get() = _state

    init {
        viewModelScope.launch {
            if (isOnline(context)) {
                _state.postValue(AsyncOperationState.LOADING)
                try {
                    asteroidRepository.refreshAsteroidList()
                    dailyImageRepository.refreshDailyImage()
                    _state.postValue(AsyncOperationState.SUCCESS)
                } catch (error: Throwable) {
                    _state.postValue(AsyncOperationState.FAILURE)
                    Toast.makeText(context, "Network call failure", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("MainViewModel", "Network call failure", error)
                }
            } else {
                Toast.makeText(context, "No Internet Connection Detected", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // https://stackoverflow.com/a/57237708
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    class Factory(private val app: Application, private val context: Context) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app, context) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}