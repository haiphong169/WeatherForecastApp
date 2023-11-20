package com.haiphong.weatherforecastapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT EXISTS(SELECT * FROM fav_tbl WHERE city = :city)")
    fun checkIfFavorite(city: String): Flow<Boolean>

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun flush()


}