package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.data.Asteroid

@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    var id: Long,
    var codeName: String,
    var absoluteMagnitude: Double,
    var estimatedDiameter: Double,
    var closeApproachDate: String,
    var relativeVelocity: Double,
    var distanceFromEarth: Double,
    var isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> = map {
    Asteroid(
        id = it.id,
        codename = it.codeName,
        closeApproachDate = it.closeApproachDate,
        absoluteMagnitude = it.absoluteMagnitude,
        estimatedDiameter = it.estimatedDiameter,
        relativeVelocity = it.relativeVelocity,
        distanceFromEarth = it.distanceFromEarth,
        isPotentiallyHazardous = it.isPotentiallyHazardous
    )
}