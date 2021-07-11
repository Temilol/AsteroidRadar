package com.udacity.asteroidradar.data

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabaseDailyImage

data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDay.asDatabaseModel(): DatabaseDailyImage = DatabaseDailyImage(
    url = url, mediaType = mediaType, title = title
)
