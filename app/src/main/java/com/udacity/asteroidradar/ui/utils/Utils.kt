package com.udacity.asteroidradar.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = Date()
    return formatter.format(date)
}