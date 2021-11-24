package com.udacity.asteroidradar.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NearEarth(
    @SerializedName("near_earth_objects")
    val nearEarthObjects: Map<String, List<NearEarthObject>>
) : Parcelable

@Parcelize
data class NearEarthObject(
    val id: Long,
    @SerializedName("name")
    val codename: String,
    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,
    @SerializedName("close_approach_data")
    val closeApproachDate: List<CloseApproachDate>,
    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
) : Parcelable

@Parcelize
data class EstimatedDiameter(
    val kilometers: Kilometer,
) : Parcelable

@Parcelize
data class Kilometer(
    @SerializedName("estimated_diameter_max")
    val estimatedDiameter: Double
) : Parcelable

@Parcelize
data class CloseApproachDate(
    @SerializedName("close_approach_date")
    val closeApproachDate: String,
    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity,
    @SerializedName("miss_distance")
    val missDistance: MissDistance,
) : Parcelable

@Parcelize
data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: Double
) : Parcelable

@Parcelize
data class MissDistance(
    val astronomical: Double
) : Parcelable