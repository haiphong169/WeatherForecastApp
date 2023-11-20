package com.haiphong.weatherforecastapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tbl")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val country: String
)