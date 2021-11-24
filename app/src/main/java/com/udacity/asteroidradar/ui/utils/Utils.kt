package com.udacity.asteroidradar.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = Date()
    return formatter.format(date)
}

fun getDateAfterToday(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val newDate = calendar.time
    return formatter.format(newDate)
}