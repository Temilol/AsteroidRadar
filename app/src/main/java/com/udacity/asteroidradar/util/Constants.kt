package com.udacity.asteroidradar.util

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov"
}

enum class AsyncOperationState {
    LOADING, SUCCESS, FAILURE
}