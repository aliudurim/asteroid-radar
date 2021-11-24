package com.udacity.asteroidradar.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PictureOfDay(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @SerializedName("media_type")
    val mediaType: String,
    val title: String,
    val url: String
) : Parcelable