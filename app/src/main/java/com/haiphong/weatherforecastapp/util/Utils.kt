package com.haiphong.weatherforecastapp.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(timestamp: Long): String {
    val localDate = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()

    return localDate.format(DateTimeFormatter.ofPattern("EEE, MMM d"))
}

fun formatTime(timestamp: Long): String {
    val localTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalTime()

    return localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun formatDegree(degree: Double): String {
    return "%.0f".format(degree) + "°"
}