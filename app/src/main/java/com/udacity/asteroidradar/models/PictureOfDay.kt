package com.udacity.asteroidradar.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureOfDay(
    @SerializedName("media_type")
    val mediaType: String,
    val title: String,
    val url: String
) : Parcelable